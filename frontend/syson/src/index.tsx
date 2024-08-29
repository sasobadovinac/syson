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

// It should be the first import
// Otherwise the following error will be thrown: styled_default is not a function
// https://github.com/vitejs/vite/issues/12423#issuecomment-2080351394
import '@mui/material/styles/styled';

import { loadDevMessages, loadErrorMessages } from '@apollo/client/dev';
import { ExtensionRegistry } from '@eclipse-sirius/sirius-components-core';
import { diagramPanelActionExtensionPoint, NodeTypeContribution } from '@eclipse-sirius/sirius-components-diagrams';
import { treeItemContextMenuEntryExtensionPoint } from '@eclipse-sirius/sirius-components-trees';
import {
  ApolloClientOptionsConfigurer,
  apolloClientOptionsConfigurersExtensionPoint,
  DiagramRepresentationConfiguration,
  footerExtensionPoint,
  navigationBarIconExtensionPoint,
  NavigationBarIconProps,
  navigationBarMenuHelpURLExtensionPoint,
  NodeTypeRegistry,
  SiriusWebApplication,
} from '@eclipse-sirius/sirius-web-application';
import {
  InsertTextualSysMLMenuContribution,
  SysMLPackageNode,
  SysMLPackageNodeConverter,
  SysMLPackageNodeLayoutHandler,
  sysMLPackageNodeStyleDocumentTransform,
  SysONDiagramPanelMenu,
} from '@eclipse-syson/syson-components';
import ReactDOM from 'react-dom';

import { SysONIcon } from './core/SysONIcon';
import { httpOrigin, wsOrigin } from './core/URL';
import { referenceWidgetDocumentTransform } from './extensions/ReferenceWidgetDocumentTransform';
import { SysONFooter } from './extensions/SysONFooter';
import { sysonTheme } from './theme/sysonTheme';

import './fonts.css';
import './reset.css';
import './variables.css';

if (process.env.NODE_ENV !== 'production') {
  loadDevMessages();
  loadErrorMessages();
}

const SysONNavigationBarIcon = ({}: NavigationBarIconProps) => {
  return <SysONIcon />;
};

const extensionRegistry: ExtensionRegistry = new ExtensionRegistry();
extensionRegistry.addComponent(navigationBarIconExtensionPoint, {
  identifier: 'syson_navigationbar#icon',
  Component: SysONNavigationBarIcon,
});
extensionRegistry.putData(navigationBarMenuHelpURLExtensionPoint, {
  identifier: 'syson_navigationBarMenu#helpURL',
  data: 'https://doc.mbse-syson.org',
});

const widgetsApolloClientOptionsConfigurer: ApolloClientOptionsConfigurer = (currentOptions) => {
  const { documentTransform } = currentOptions;

  const newDocumentTransform = documentTransform
    ? documentTransform.concat(referenceWidgetDocumentTransform)
    : referenceWidgetDocumentTransform;
  return {
    ...currentOptions,
    documentTransform: newDocumentTransform,
  };
};

const apolloClientOptionsConfigurer: ApolloClientOptionsConfigurer = (currentOptions) => {
  const { documentTransform } = currentOptions;

  const newDocumentTransform = documentTransform
    ? documentTransform.concat(sysMLPackageNodeStyleDocumentTransform)
    : sysMLPackageNodeStyleDocumentTransform;
  return {
    ...currentOptions,
    documentTransform: newDocumentTransform,
  };
};
extensionRegistry.putData(apolloClientOptionsConfigurersExtensionPoint, {
  identifier: `siriusWeb_${apolloClientOptionsConfigurersExtensionPoint.identifier}`,
  data: [apolloClientOptionsConfigurer, widgetsApolloClientOptionsConfigurer],
});

extensionRegistry.addComponent(diagramPanelActionExtensionPoint, {
  identifier: `syson_${diagramPanelActionExtensionPoint.identifier}_CustomPanelEntriesMenu`,
  Component: SysONDiagramPanelMenu,
});

extensionRegistry.addComponent(treeItemContextMenuEntryExtensionPoint, {
  identifier: `syson${treeItemContextMenuEntryExtensionPoint.identifier}_insertTextualSysML`,
  Component: InsertTextualSysMLMenuContribution,
});

extensionRegistry.addComponent(footerExtensionPoint, {
  identifier: `syson_${footerExtensionPoint.identifier}`,
  Component: SysONFooter,
});

const nodeTypeRegistry: NodeTypeRegistry = {
  nodeLayoutHandlers: [new SysMLPackageNodeLayoutHandler()],
  nodeConverters: [new SysMLPackageNodeConverter()],
  nodeTypeContributions: [<NodeTypeContribution component={SysMLPackageNode} type={'sysMLPackageNode'} />],
};

ReactDOM.render(
  <SiriusWebApplication
    httpOrigin={httpOrigin}
    wsOrigin={wsOrigin}
    theme={sysonTheme}
    extensionRegistry={extensionRegistry}>
    <DiagramRepresentationConfiguration nodeTypeRegistry={nodeTypeRegistry} />
  </SiriusWebApplication>,
  document.getElementById('root')
);
