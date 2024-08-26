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

import { Project } from '../../../../pages/Project';
import { SysMLv2 } from '../../../../usecases/SysMLv2';
import { Diagram } from '../../../../workbench/Diagram';
import { Explorer } from '../../../../workbench/Explorer';
import { Workbench } from '../../../../workbench/Workbench';

describe('Drop From Explorer Tests', () => {
  const sysmlv2 = new SysMLv2();
  const diagramLabel = 'General View';

  context('Given a SysMLv2 project with a General View diagram', () => {
    const diagram = new Diagram();
    const explorer = new Explorer();
    const workbench = new Workbench();
    let projectId: string = '';
    beforeEach(() =>
      sysmlv2.createSysMLv2Project().then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(projectId);
        explorer.getExplorerView().contains(sysmlv2.getProjectLabel());
        explorer.expand(sysmlv2.getProjectLabel());
        explorer.getExplorerView().contains(sysmlv2.getRootElementLabel());
        explorer.expand(sysmlv2.getRootElementLabel());
        explorer.select(diagramLabel);
        diagram.getDiagram(diagramLabel).should('exist');
        // Wait for the arrange all action to complete
        /* eslint-disable-next-line cypress/no-unnecessary-waiting */
        cy.wait(400);
      })
    );

    afterEach(() => cy.deleteProject(projectId));

    context('When we create a PartUsage with an AttributeUsage in the root Package in the explorer', () => {
      beforeEach(() => {
        explorer.createObject(sysmlv2.getRootElementLabel(), 'SysMLv2EditService-PartUsage');
        explorer.createObject('part', 'SysMLv2EditService-AttributeUsage');
        explorer.getTreeItemByLabel('part').should('exist');
        explorer
          .getTreeItemByLabel('attribute')
          .should('exist')
          .parents('ul')
          .first()
          .siblings()
          .contains('part')
          .should('exist');
      });

      it('Then we can drop the PartUsage on the diagram, and its compartment are not visible', () => {
        const dataTransfer = new DataTransfer();
        explorer.dragTreeItem('part', dataTransfer);
        diagram.dropOnDiagram(diagramLabel, dataTransfer);

        diagram.getNodes(diagramLabel, 'part').should('exist');
        // Check that the compartments of the node aren't visible
        diagram.getNodes(diagramLabel, 'attributes').should('not.exist');
      });

      it('Then when we drop the PartUsage on the diagram twice, it is only represented once', () => {
        const dataTransfer = new DataTransfer();
        explorer.dragTreeItem('part', dataTransfer);
        diagram.dropOnDiagram(diagramLabel, dataTransfer);
        diagram.dropOnDiagram(diagramLabel, dataTransfer);

        diagram.getNodes(diagramLabel, 'part').should('exist').should('have.length', 1);
        diagram.getNodes(diagramLabel, 'attributes').should('not.exist');
        workbench.getSnackbar().should('exist').contains('The element part is already visible in its parent Package 1');
      });

      it('Then when we drop the PartUsage on the diagram, hide it, and drop it again, the PartUsage is visible on the diagram', () => {
        const dataTransfer = new DataTransfer();
        explorer.dragTreeItem('part', dataTransfer);
        diagram.dropOnDiagram(diagramLabel, dataTransfer);
        diagram.getNodes(diagramLabel, 'part').should('exist').should('have.length', 1).click();
        diagram.getPaletteToolSection(3).click();
        diagram.getNodes(diagramLabel, 'part').should('not.exist');
        diagram.dropOnDiagram(diagramLabel, dataTransfer);
        diagram.getNodes(diagramLabel, 'part').should('exist').should('have.length', 1);
        diagram.getNodes(diagramLabel, 'attributes').should('not.exist');
      });
    });
  });
});
