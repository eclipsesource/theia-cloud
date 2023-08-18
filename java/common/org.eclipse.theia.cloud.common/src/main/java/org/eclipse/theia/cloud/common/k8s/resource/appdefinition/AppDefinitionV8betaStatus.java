/********************************************************************************
 * Copyright (C) 2023 EclipseSource and others.
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
package org.eclipse.theia.cloud.common.k8s.resource.appdefinition;

import org.eclipse.theia.cloud.common.k8s.resource.ResourceStatus;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class AppDefinitionV8betaStatus extends ResourceStatus {
    // This class is empty as only the common properties of the super class are
    // used. Already define a specific class to allow easier extension, properly
    // type the resources and resource clients.
    // It is planned to extend this later with AppDefinition specific status steps.

    /**
     * Default constructor.
     */
    public AppDefinitionV8betaStatus() {
    }

    /**
     * Migration constructor.
     * 
     * @param toMigrate
     */
    @SuppressWarnings("deprecation")
    public AppDefinitionV8betaStatus(
	    org.eclipse.theia.cloud.common.k8s.resource.appdefinition.v7.AppDefinitionV7betaStatus toMigrate) {
	setOperatorStatus(toMigrate.getOperatorStatus());
	setOperatorMessage(toMigrate.getOperatorMessage());
    }
}