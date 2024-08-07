/********************************************************************************
 * Copyright (C) 2022 EclipseSource and others.
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
package org.eclipse.theia.cloud.service;

import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "EnvironmentVars", description = "An object to hold all the ways environment variables can be passed. Not to be used by itself.")
public class EnvironmentVars {
    public EnvironmentVars() {

    }

    @Schema(description = "Map of environment variables to be passed to Deployment. "
            + " Ignored if Theia applications are started eagerly.  Empty by default.", required = false)
    public Map<String, String> fromMap = Map.of();

    @Schema(description = "List of ConfigMaps (by name) containing environment variables to be passed to Deployment as envFrom.configMapRef. "
            + " Ignored if Theia applications are started eagerly.  Empty by default.", required = false)
    public List<String> fromConfigMaps = List.of();

    @Schema(description = "List of Secrets (by name) containing environment variables to be passed to Deployment as envFrom.secretRef. "
            + " Ignored if Theia applications are started eagerly.  Empty by default.", required = false)
    public List<String> fromSecrets = List.of();

    @Override
    public String toString() {
        return "LaunchRequest.Env[fromMap=" + fromMap.toString() + ", fromConfigMaps=" + fromConfigMaps.toString()
                + ", fromSecrets=" + fromSecrets.toString() + "]";
    }
}
