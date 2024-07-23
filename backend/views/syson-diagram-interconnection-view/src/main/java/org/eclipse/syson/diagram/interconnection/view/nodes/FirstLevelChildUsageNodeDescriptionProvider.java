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
package org.eclipse.syson.diagram.interconnection.view.nodes;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DropNodeTool;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.FreeFormLayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.InsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.InsideLabelStyle;
import org.eclipse.sirius.components.view.diagram.LabelTextAlign;
import org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.components.view.diagram.UserResizableDirection;
import org.eclipse.syson.diagram.common.view.nodes.AbstractNodeDescriptionProvider;
import org.eclipse.syson.diagram.interconnection.view.IVDescriptionNameGenerator;
import org.eclipse.syson.diagram.interconnection.view.InterconnectionViewDiagramDescriptionProvider;
import org.eclipse.syson.diagram.interconnection.view.services.InterconnectionViewNodeToolSectionSwitch;
import org.eclipse.syson.sysml.SysmlPackage;
import org.eclipse.syson.util.AQLUtils;
import org.eclipse.syson.util.SysMLMetamodelHelper;
import org.eclipse.syson.util.ViewConstants;

/**
 * Used to create the child part usage node description.
 *
 * @author arichard
 */
public class FirstLevelChildUsageNodeDescriptionProvider extends AbstractNodeDescriptionProvider {

    private final IVDescriptionNameGenerator descriptionNameGenerator;

    private final String semanticCandidateService;

    private final EClass eClass;

    public FirstLevelChildUsageNodeDescriptionProvider(EClass eClass, String semanticCandidateService, IColorProvider colorProvider, IVDescriptionNameGenerator descriptionNameGenerator) {
        super(colorProvider);
        this.semanticCandidateService = Objects.requireNonNull(semanticCandidateService);
        this.descriptionNameGenerator = Objects.requireNonNull(descriptionNameGenerator);
        this.eClass = Objects.requireNonNull(eClass);
    }

    @Override
    public NodeDescription create() {
        String domainType = SysMLMetamodelHelper.buildQualifiedName(this.eClass);
        return this.diagramBuilderHelper.newNodeDescription()
                .childrenLayoutStrategy(this.diagramBuilderHelper.newListLayoutStrategyDescription().areChildNodesDraggableExpression("false").build())
                .collapsible(true)
                .defaultHeightExpression(ViewConstants.DEFAULT_CONTAINER_NODE_HEIGHT)
                .defaultWidthExpression("150")
                .domainType(domainType)
                .insideLabel(this.createInsideLabelDescription())
                .name(this.descriptionNameGenerator.getFirstLevelNodeName(this.eClass))
                .semanticCandidatesExpression(AQLUtils.getSelfServiceCallExpression(this.semanticCandidateService))
                .style(this.createChildUsageNodeStyle())
                .userResizable(UserResizableDirection.BOTH)
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var reusedChildren = new LinkedHashSet<NodeDescription>();

        var optChildUsageNodeDescription = cache.getNodeDescription(this.descriptionNameGenerator.getFirstLevelNodeName(this.eClass));
        InterconnectionViewDiagramDescriptionProvider.COMPARTMENTS_WITH_LIST_ITEMS.forEach((type, listItems) -> {
            if (type.equals(this.eClass)) {
                listItems.forEach(eReference -> {
                    cache.getNodeDescription(this.descriptionNameGenerator.getCompartmentName(type, eReference)).ifPresent(reusedChildren::add);
                });
            }
        });
        InterconnectionViewDiagramDescriptionProvider.COMPARTMENTS_WITH_FREE_FORM_ITEMS.forEach((type, listItems) -> {
            if (type.equals(this.eClass)) {
                listItems.forEach(eReference -> {
                    cache.getNodeDescription(this.descriptionNameGenerator.getFreeFormCompartmentName(type, eReference)).ifPresent(node -> {
                        reusedChildren.add(node);
                    });
                });
            }
        });

        var optPortUsageBorderNodeDescription = cache.getNodeDescription(this.descriptionNameGenerator.getBorderNodeName(SysmlPackage.eINSTANCE.getPortUsage()));

        NodeDescription nodeDescription = optChildUsageNodeDescription.get();
        nodeDescription.getReusedChildNodeDescriptions().addAll(reusedChildren);
        nodeDescription.getReusedBorderNodeDescriptions().add(optPortUsageBorderNodeDescription.get());
        nodeDescription.setPalette(this.createNodePalette(cache));

        List<NodeDescription> growableNodes = new ArrayList<>();
        nodeDescription.getReusedChildNodeDescriptions().stream()
                .filter(nodeDesc -> nodeDesc.getChildrenLayoutStrategy() instanceof FreeFormLayoutStrategyDescription)
                .forEach(growableNodes::add);

        ListLayoutStrategyDescription layoutStrategy = this.diagramBuilderHelper.newListLayoutStrategyDescription()
                .areChildNodesDraggableExpression("false")
                .growableNodes(growableNodes.toArray(NodeDescription[]::new))
                .build();
        nodeDescription.setChildrenLayoutStrategy(layoutStrategy);
    }

    private InsideLabelDescription createInsideLabelDescription() {
        return this.diagramBuilderHelper.newInsideLabelDescription()
                .labelExpression(AQLUtils.getSelfServiceCallExpression("getContainerLabel"))
                .position(InsideLabelPosition.TOP_CENTER)
                .style(this.createInsideLabelStyle())
                .textAlign(LabelTextAlign.CENTER)
                .build();
    }

    private InsideLabelStyle createInsideLabelStyle() {
        return this.diagramBuilderHelper.newInsideLabelStyle()
                .borderSize(0)
                .displayHeaderSeparator(true)
                .labelColor(this.colorProvider.getColor(ViewConstants.DEFAULT_LABEL_COLOR))
                .showIconExpression(AQLUtils.getSelfServiceCallExpression("showIcon"))
                .withHeader(true)
                .build();
    }

    private NodeStyleDescription createChildUsageNodeStyle() {
        return this.diagramBuilderHelper.newRectangularNodeStyleDescription()
                .borderColor(this.colorProvider.getColor(ViewConstants.DEFAULT_BORDER_COLOR))
                .borderRadius(10)
                .background(this.colorProvider.getColor(ViewConstants.DEFAULT_BACKGROUND_COLOR))
                .build();
    }

    private NodePalette createNodePalette(IViewDiagramElementFinder cache) {
        var changeContext = this.viewBuilderHelper.newChangeContext()
                .expression(AQLUtils.getSelfServiceCallExpression("deleteFromModel"));

        var deleteTool = this.diagramBuilderHelper.newDeleteTool()
                .name("Delete from Model")
                .body(changeContext.build());

        var callEditService = this.viewBuilderHelper.newChangeContext()
                .expression(AQLUtils.getSelfServiceCallExpression("directEdit", "newLabel"));

        var editTool = this.diagramBuilderHelper.newLabelEditTool()
                .name("Edit")
                .initialDirectEditLabelExpression(AQLUtils.getSelfServiceCallExpression("getDefaultInitialDirectEditLabel"))
                .body(callEditService.build());

        List<NodeToolSection> toolSections = new ArrayList<>();
        toolSections.addAll(this.createNodeToolSections(cache));
        this.orderToolSectionsTools(toolSections);
        toolSections.add(this.defaultToolsFactory.createDefaultHideRevealNodeToolSection());

        return this.diagramBuilderHelper.newNodePalette()
                .deleteTool(deleteTool.build())
                .dropNodeTool(this.createDropFromDiagramTool(cache))
                .labelEditTool(editTool.build())
                .toolSections(toolSections.toArray(NodeToolSection[]::new))
                .edgeTools(this.getEdgeTools(cache).toArray(EdgeTool[]::new))
                .build();
    }

    private List<NodeToolSection> createNodeToolSections(IViewDiagramElementFinder cache) {
        InterconnectionViewNodeToolSectionSwitch toolSectionSwitch = new InterconnectionViewNodeToolSectionSwitch(cache);
        return toolSectionSwitch.doSwitch(this.eClass);
    }

    private List<EdgeTool> getEdgeTools(IViewDiagramElementFinder cache) {
        return List.of();
    }

    private DropNodeTool createDropFromDiagramTool(IViewDiagramElementFinder cache) {
        var acceptedNodeTypes = new ArrayList<NodeDescription>();

        var optPortUsageBorderNodeDescription = cache.getNodeDescription(this.descriptionNameGenerator.getBorderNodeName(SysmlPackage.eINSTANCE.getPortUsage()));
        var optChildPartUsageNodeDescription = cache.getNodeDescription(this.descriptionNameGenerator.getNodeName(SysmlPackage.eINSTANCE.getPartUsage()));
        var optFirstLevelChildPartUsageNodeDescription = cache.getNodeDescription(this.descriptionNameGenerator.getFirstLevelNodeName(SysmlPackage.eINSTANCE.getPartUsage()));

        acceptedNodeTypes.add(optPortUsageBorderNodeDescription.get());
        acceptedNodeTypes.add(optChildPartUsageNodeDescription.get());
        acceptedNodeTypes.add(optFirstLevelChildPartUsageNodeDescription.get());

        var dropElementFromDiagram = this.viewBuilderHelper.newChangeContext()
                .expression(AQLUtils.getServiceCallExpression("droppedElement", "dropElementFromDiagram", List.of("droppedNode", "targetElement", "targetNode", "editingContext", "diagramContext",
                        "convertedNodes")));

        return this.diagramBuilderHelper.newDropNodeTool()
                .name("Drop from Diagram")
                .acceptedNodeTypes(acceptedNodeTypes.toArray(NodeDescription[]::new))
                .body(dropElementFromDiagram.build())
                .build();
    }
}
