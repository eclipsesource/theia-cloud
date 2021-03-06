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
package org.eclipse.theia.cloud.operator.handler;

import static org.eclipse.theia.cloud.common.util.LogMessageUtil.formatLogMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.theia.cloud.common.k8s.resource.Session;
import org.eclipse.theia.cloud.common.k8s.resource.SessionSpec;
import org.eclipse.theia.cloud.common.k8s.resource.SessionSpecResourceList;
import org.eclipse.theia.cloud.operator.resource.AppDefinitionSpec;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;

public final class TheiaCloudK8sUtil {

    private static final Logger LOGGER = LogManager.getLogger(TheiaCloudK8sUtil.class);

    private TheiaCloudK8sUtil() {
    }

    public static boolean checkIfMaxInstancesReached(DefaultKubernetesClient client, String namespace,
	    SessionSpec sessionSpec, AppDefinitionSpec appDefinitionSpec, String correlationId) {

	if (appDefinitionSpec.getMaxInstances() < 1) {
	    LOGGER.info(formatLogMessage(correlationId,
		    "App Definition " + appDefinitionSpec.getName() + " allows indefinite sessions."));
	    return false;
	}

	long currentInstances = client.customResources(Session.class, SessionSpecResourceList.class)
		.inNamespace(namespace).list().getItems().stream()//
		.filter(w -> {
		    String appDefinition = w.getSpec().getAppDefinition();
		    boolean result = appDefinition.equals(appDefinitionSpec.getName());
		    LOGGER.trace(formatLogMessage(correlationId, "Counting instances of app definition "
			    + appDefinitionSpec.getName() + ": Is " + w.getSpec() + " of app definition? " + result));
		    return result;
		})//
		.count();
	return currentInstances > appDefinitionSpec.getMaxInstances();
    }

}
