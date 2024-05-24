/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.syson.sysml.helper;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.syson.sysml.Conjugation;
import org.eclipse.syson.sysml.Element;
import org.eclipse.syson.sysml.Import;
import org.eclipse.syson.sysml.Membership;
import org.eclipse.syson.sysml.MembershipImport;
import org.eclipse.syson.sysml.Namespace;
import org.eclipse.syson.sysml.NamespaceImport;
import org.eclipse.syson.sysml.Specialization;
import org.eclipse.syson.sysml.SysmlPackage;
import org.eclipse.syson.sysml.Type;
import org.eclipse.syson.sysml.Usage;
import org.eclipse.syson.sysml.VisibilityKind;

/**
 * Object in charge of computing:
 * <ul>
 * <li>visibleMemberships</li>
 * <li>inheritedMemberships</li>
 * <li>importedMemberships</li>
 * </ul>
 * 
 * @author Arthur Daussy
 */
public class MembershipComputer<T extends Element> {

    private Set<Element> visited;

    private T sourceElement;

    public MembershipComputer(T sourceElement, EList<? extends Namespace> excluded) {
        super();
        this.visited = excluded.stream().collect(toSet());
        this.sourceElement = sourceElement;
    }

    public EList<Membership> visibleMemberships(boolean isRecursive, boolean includeAll, boolean includeProtectedInherited) {

        if (sourceElement instanceof Namespace namespace) {
            return visibleMemberships(namespace, isRecursive, includeAll, includeProtectedInherited);
        } else {
            return new BasicEList<Membership>();
        }

    }

    private EList<Membership> visibleMemberships(Namespace self, boolean isRecursive, boolean includeAll, boolean includeProtectedInherited) {

        if (visited.contains(self)) {
            return new BasicEList<Membership>();
        }

        // Protected against infinite loop while iterating on imported/inherited elements
        visited.add(self);

        NameConflictingFilter nameConflictingFilter = new NameConflictingFilter();
        List<Membership> directMemberships = self.getOwnedMembership().stream()
                .filter(m -> includeAll || m.getVisibility() == VisibilityKind.PUBLIC)
                .filter(nameConflictingFilter)
                .collect(toList());

        self.getOwnedImport().stream()
                .filter(m -> includeAll || m.getVisibility() == VisibilityKind.PUBLIC)
                .flatMap(imp -> importedMemberships(imp).stream())
                .filter(nameConflictingFilter)
                .forEach(directMemberships::add);

        if (self instanceof Type type) {

            inheritedMemberships(type).stream()
                    .filter(rel -> includeAll || (includeProtectedInherited && rel.getVisibility() == VisibilityKind.PROTECTED) || rel.getVisibility() == VisibilityKind.PUBLIC)
                    .filter(nameConflictingFilter)
                    .forEach(directMemberships::add);

        }

        BasicEList<Membership> visibleMemberships = new BasicEList<Membership>(directMemberships);

        if (isRecursive) {

            List<Membership> recursiveMembers = new BasicEList<Membership>();
            for (Membership m : visibleMemberships) {
                if (m.getMemberElement() instanceof Namespace subNamespace) {
                    if (!visibleMemberships.contains(subNamespace)) {
                        recursiveMembers.addAll(visibleMemberships(subNamespace, isRecursive, includeAll, includeProtectedInherited));
                    }
                }
            }
            visibleMemberships.addAll(recursiveMembers);
        }

        return visibleMemberships;
    }

    public <T extends Namespace> EList<Membership> inheritedMemberships() {

        if (sourceElement instanceof Type type) {
            return inheritedMemberships(type);
        } else {
            return new BasicEList<Membership>();
        }
    }

    private <T extends Namespace> EList<Membership> inheritedMemberships(Type self) {

        visited.add((Namespace) self);

        NameConflictingFilter namefilter = new NameConflictingFilter();
        namefilter.fillUsedNames(self.getOwnedMembership());
        Conjugation conjugator = self.getOwnedConjugator();
        List<Membership> conjugatedMemberships = List.of();
        if (conjugator != null) {
            Type type = conjugator.getOriginalType();
            if (type != null) {
                conjugatedMemberships = visibleMemberships(type, false, true, true).stream().filter(namefilter).toList();
            }
        }

        List<Membership> generalMemberships = new BasicEList<Membership>();
        for (Specialization specialization : self.getOwnedSpecialization()) {
            Type general = specialization.getGeneral();
            if (general != null && !visited.contains(general)) {
                visibleMemberships(general, false, true, true).stream()
                        .filter(namefilter)
                        .forEach(generalMemberships::add);
            }
        }
        if(self instanceof Usage usage) {
            Usage owningUsage = usage.getOwningUsage();
            if (owningUsage != null) {
                generalMemberships.addAll(inheritedMemberships(owningUsage));
                owningUsage.getOwnedFeatureMembership().stream()
                        .filter(m -> m.getVisibility() != VisibilityKind.PRIVATE)
                        .forEach(generalMemberships::add);
            }
        }

        Membership[] data = Stream.concat(conjugatedMemberships.stream(), generalMemberships.stream())
                // Also inherit protected memberships
                .filter(rel -> rel.getVisibility() != VisibilityKind.PRIVATE)
                .toArray(Membership[]::new);
        return new EcoreEList.UnmodifiableEList<Membership>((InternalEObject) self, SysmlPackage.eINSTANCE.getType_InheritedMembership(), data.length, data);
    }

    public EList<Membership> importedMemberships() {

        if (sourceElement instanceof Import imp) {
            return importedMemberships(imp);
        } else {
            return new BasicEList<Membership>();
        }
    }

    private EList<Membership> importedMemberships(Import self) {
        if (self instanceof NamespaceImport nmImport) {
            return importedMemberships(nmImport);
        } else if (self instanceof MembershipImport msImport) {
            return importedMemberships(msImport);
        }
        return new BasicEList<Membership>();
    }

    private EList<Membership> importedMemberships(MembershipImport msImport) {

        BasicEList<Membership> importedMemberships = new BasicEList<Membership>();
        Membership membership = msImport.getImportedMembership();

        if (membership != null) {

            Element member = membership.getMemberElement();

            if (member != null) {
                if (!msImport.isIsRecursive() || !(member instanceof Namespace)) {
                    importedMemberships.add(membership);
                } else if (member instanceof Namespace namespace) {
                    if (!visited.contains(namespace)) {
                        importedMemberships.add(membership);
                        importedMemberships.addAll(visibleMemberships(namespace, msImport.isIsRecursive(), msImport.isIsImportAll(), false));
                    }
                }
            }
        }

        return importedMemberships;
    }

    private EList<Membership> importedMemberships(NamespaceImport self) {

        Namespace aImportedNamespace = self.getImportedNamespace();
        BasicEList<Membership> result = new BasicEList<>();
        if (aImportedNamespace != null && !visited.contains(aImportedNamespace)) {
            result.addAll(visibleMemberships(aImportedNamespace, self.isIsRecursive(), self.isIsImportAll(), false));
        }
        return result;
    }

}
