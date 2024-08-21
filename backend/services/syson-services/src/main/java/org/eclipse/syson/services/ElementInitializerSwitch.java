/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.syson.services;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.syson.sysml.AcceptActionUsage;
import org.eclipse.syson.sysml.ActorMembership;
import org.eclipse.syson.sysml.AttributeUsage;
import org.eclipse.syson.sysml.BindingConnectorAsUsage;
import org.eclipse.syson.sysml.ConjugatedPortDefinition;
import org.eclipse.syson.sysml.Definition;
import org.eclipse.syson.sysml.Dependency;
import org.eclipse.syson.sysml.Documentation;
import org.eclipse.syson.sysml.Element;
import org.eclipse.syson.sysml.EnumerationDefinition;
import org.eclipse.syson.sysml.FeatureDirectionKind;
import org.eclipse.syson.sysml.FeatureTyping;
import org.eclipse.syson.sysml.FlowConnectionUsage;
import org.eclipse.syson.sysml.ObjectiveMembership;
import org.eclipse.syson.sysml.OwningMembership;
import org.eclipse.syson.sysml.Package;
import org.eclipse.syson.sysml.ParameterMembership;
import org.eclipse.syson.sysml.PartUsage;
import org.eclipse.syson.sysml.PerformActionUsage;
import org.eclipse.syson.sysml.PortConjugation;
import org.eclipse.syson.sysml.PortDefinition;
import org.eclipse.syson.sysml.Redefinition;
import org.eclipse.syson.sysml.ReferenceSubsetting;
import org.eclipse.syson.sysml.ReferenceUsage;
import org.eclipse.syson.sysml.RequirementUsage;
import org.eclipse.syson.sysml.Specialization;
import org.eclipse.syson.sysml.Subclassification;
import org.eclipse.syson.sysml.SubjectMembership;
import org.eclipse.syson.sysml.Subsetting;
import org.eclipse.syson.sysml.SuccessionAsUsage;
import org.eclipse.syson.sysml.SysmlFactory;
import org.eclipse.syson.sysml.TransitionUsage;
import org.eclipse.syson.sysml.Usage;
import org.eclipse.syson.sysml.UseCaseDefinition;
import org.eclipse.syson.sysml.UseCaseUsage;
import org.eclipse.syson.sysml.util.SysmlSwitch;

/**
 * Switch called when a new element is created. Allows to set various attributes/references.
 *
 * @author arichard
 */
public class ElementInitializerSwitch extends SysmlSwitch<Element> {

    @Override
    public Element defaultCase(EObject object) {
        if (object instanceof Element element) {
            return element;
        }
        return null;
    }

    @Override
    public Element caseAcceptActionUsage(AcceptActionUsage object) {
        this.caseUsage(object);
        // create two ParameterMembership
        object.getOwnedRelationship().add(this.createParameterMembershipWithReferenceUsage("payload", FeatureDirectionKind.INOUT));
        object.getOwnedRelationship().add(this.createParameterMembershipWithReferenceUsage("receiver", FeatureDirectionKind.IN));
        return object;
    }

    @Override
    public Element caseAttributeUsage(AttributeUsage object) {
        this.caseUsage(object);
        // An AttributeUsage is always referential (isReference = true and isComposite = false).
        object.setIsComposite(false);
        return object;
    }

    @Override
    public Element caseBindingConnectorAsUsage(BindingConnectorAsUsage object) {
        object.setDeclaredName("bind");
        return object;
    }

    @Override
    public Element caseDependency(Dependency object) {
        object.setDeclaredName("dependency");
        return object;
    }

    @Override
    public Element caseDefinition(Definition object) {
        object.setDeclaredName(object.eClass().getName());
        return object;
    }

    @Override
    public Element caseDocumentation(Documentation object) {
        object.setBody("add doc here");
        return object;
    }

    @Override
    public Element caseElement(Element object) {
        return object;
    }

    @Override
    public Element caseEnumerationDefinition(EnumerationDefinition object) {
        object.setIsVariation(true);
        object.setDeclaredName(object.eClass().getName());
        return object;
    }

    @Override
    public Element caseFeatureTyping(FeatureTyping object) {
        object.setDeclaredName("typed by");
        return object;
    }

    @Override
    public Element caseFlowConnectionUsage(FlowConnectionUsage object) {
        object.setDeclaredName("flow");
        return object;
    }

    @Override
    public Element casePackage(Package object) {
        object.setDeclaredName(object.eClass().getName());
        return object;
    }

    @Override
    public Element casePartUsage(PartUsage object) {
        this.caseUsage(object);
        if (object.getOwningMembership() instanceof ActorMembership) {
            object.setDeclaredName("actor");
        }
        return object;
    }

    @Override
    public Element casePerformActionUsage(PerformActionUsage object) {
        // no name for new perform action
        return object;
    }

    @Override
    public Element casePortDefinition(PortDefinition object) {
        object.setDeclaredName(object.eClass().getName());
        OwningMembership owningMembership = SysmlFactory.eINSTANCE.createOwningMembership();
        object.getOwnedRelationship().add(owningMembership);
        // No need to set the declaredName for the ConjugatedPortDefinition here, it is always the same than its
        // originalPortDefinition and computed elsewhere
        ConjugatedPortDefinition conjugatedPortDefinition = SysmlFactory.eINSTANCE.createConjugatedPortDefinition();
        owningMembership.getOwnedRelatedElement().add(conjugatedPortDefinition);
        PortConjugation portConjugation = SysmlFactory.eINSTANCE.createPortConjugation();
        conjugatedPortDefinition.getOwnedRelationship().add(portConjugation);
        portConjugation.setConjugatedType(conjugatedPortDefinition);
        portConjugation.setOriginalPortDefinition(object);
        return object;
    }

    @Override
    public Element caseRedefinition(Redefinition object) {
        object.setDeclaredName("redefines");
        return object;
    }

    @Override
    public Element caseReferenceSubsetting(ReferenceSubsetting object) {
        object.setDeclaredName("references");
        return object;
    }

    @Override
    public Element caseReferenceUsage(ReferenceUsage object) {
        this.caseUsage(object);
        if (object.getOwningMembership() instanceof SubjectMembership) {
            object.setDeclaredName("subject");
        }
        return object;
    }

    @Override
    public Element caseRequirementUsage(RequirementUsage object) {
        this.caseUsage(object);
        if (object.getOwningMembership() instanceof ObjectiveMembership) {
            if (object.getOwner() instanceof UseCaseUsage || object.getOwner() instanceof UseCaseDefinition) {
                object.setDeclaredName(object.getOwner().getName() + "'s objective");
            }
        }
        return object;
    }

    @Override
    public Element caseSpecialization(Specialization object) {
        object.setDeclaredName("specializes");
        return object;
    }

    @Override
    public Element caseSubclassification(Subclassification object) {
        object.setDeclaredName("specializes");
        return object;
    }

    @Override
    public Element caseSubsetting(Subsetting object) {
        object.setDeclaredName("subsets");
        return object;
    }

    @Override
    public Element caseSuccessionAsUsage(SuccessionAsUsage object) {
        object.setDeclaredName("succession");
        return object;
    }

    @Override
    public Element caseTransitionUsage(TransitionUsage object) {
        StringBuilder label = new StringBuilder();
        var source = object.getSource();
        var target = object.getTarget();
        if (source != null && target != null) {
            label = label.append(source.getName()).append("_to_").append(target.getName());
        } else {
            label.append("transition");
        }
        object.setDeclaredName(label.toString());
        return object;
    }

    @Override
    public Element caseUsage(Usage object) {
        char[] charArray = object.eClass().getName().toCharArray();
        charArray[0] = Character.toLowerCase(charArray[0]);
        String defaultName = new String(charArray);
        if (defaultName.endsWith("Usage")) {
            defaultName = defaultName.substring(0, defaultName.length() - 5);
        }
        object.setDeclaredName(defaultName);
        object.setIsComposite(true);
        return object;
    }

    private ParameterMembership createParameterMembershipWithReferenceUsage(String refName, FeatureDirectionKind direction) {
        var reference = SysmlFactory.eINSTANCE.createReferenceUsage();
        reference.setDirection(direction);
        reference.setDeclaredName(refName);
        var pm = SysmlFactory.eINSTANCE.createParameterMembership();
        pm.getOwnedRelatedElement().add(reference);
        return pm;
    }
}
