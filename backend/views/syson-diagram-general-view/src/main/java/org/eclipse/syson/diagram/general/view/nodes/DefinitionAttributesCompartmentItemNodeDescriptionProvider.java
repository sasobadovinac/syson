/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.syson.diagram.general.view.nodes;

import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.syson.sysml.SysmlPackage;
import org.eclipse.syson.util.AQLConstants;
import org.eclipse.syson.util.SysMLMetamodelHelper;
import org.eclipse.syson.util.ViewConstants;

/**
 * Used to create the Definition attributes compartment item node description.
 *
 * @author arichard
 */
public class DefinitionAttributesCompartmentItemNodeDescriptionProvider extends AbstractNodeDescriptionProvider {

    public static final String NAME = "GV Node DefinitionAttributesCompartmentItem";

    public DefinitionAttributesCompartmentItemNodeDescriptionProvider(IColorProvider colorProvider) {
        super(colorProvider);
    }

    @Override
    public NodeDescription create() {
        return this.diagramBuilderHelper.newNodeDescription()
                .defaultHeightExpression(ViewConstants.DEFAULT_COMPARTMENT_NODE_ITEM_HEIGHT)
                .defaultWidthExpression(ViewConstants.DEFAULT_NODE_WIDTH)
                .domainType(SysMLMetamodelHelper.buildQualifiedName(SysmlPackage.eINSTANCE.getAttributeUsage()))
                .labelExpression(AQLConstants.AQL_SELF + ".getCompartmentItemUsageLabel()")
                .name(NAME)
                .semanticCandidatesExpression(AQLConstants.AQL_SELF + "." + SysmlPackage.eINSTANCE.getDefinition_OwnedAttribute().getName())
                .style(this.createDefinitionCompartmentItemNodeStyle())
                .userResizable(false)
                .palette(this.createCompartmentItemNodePalette())
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .build();
    }
}