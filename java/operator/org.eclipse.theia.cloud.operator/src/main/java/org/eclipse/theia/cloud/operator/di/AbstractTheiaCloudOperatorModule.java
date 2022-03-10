/********************************************************************************
 * Copyright (C) 2022 EclipseSource, Lockular, Ericsson, STMicroelectronics and 
 * others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ********************************************************************************/
package org.eclipse.theia.cloud.operator.di;

import org.eclipse.theia.cloud.operator.handler.IngressPathProvider;
import org.eclipse.theia.cloud.operator.handler.PersistentVolumeHandler;
import org.eclipse.theia.cloud.operator.handler.TemplateAddedHandler;
import org.eclipse.theia.cloud.operator.handler.WorkspaceAddedHandler;
import org.eclipse.theia.cloud.operator.handler.WorkspaceRemovedHandler;
import org.eclipse.theia.cloud.operator.handler.impl.GKEPersistentVolumeHandlerImpl;
import org.eclipse.theia.cloud.operator.handler.impl.IngressPathProviderImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public abstract class AbstractTheiaCloudOperatorModule extends AbstractModule {

    @Override
    protected void configure() {
	bind(PersistentVolumeHandler.class).to(bindPersistentVolumeHandler()).in(Singleton.class);
	bind(IngressPathProvider.class).to(bindIngressPathProvider()).in(Singleton.class);
	bind(TemplateAddedHandler.class).to(bindTemplateAddedHandler()).in(Singleton.class);
	bind(WorkspaceAddedHandler.class).to(bindWorkspaceAddedHandler()).in(Singleton.class);
	bind(WorkspaceRemovedHandler.class).to(bindWorkspaceRemovedHandler()).in(Singleton.class);

    }

    protected Class<? extends PersistentVolumeHandler> bindPersistentVolumeHandler() {
	return GKEPersistentVolumeHandlerImpl.class;
    }

    protected Class<? extends IngressPathProvider> bindIngressPathProvider() {
	return IngressPathProviderImpl.class;
    }

    protected abstract Class<? extends TemplateAddedHandler> bindTemplateAddedHandler();

    protected abstract Class<? extends WorkspaceAddedHandler> bindWorkspaceAddedHandler();

    protected abstract Class<? extends WorkspaceRemovedHandler> bindWorkspaceRemovedHandler();

}
