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
package org.eclipse.theia.cloud.service.session;

import org.eclipse.theia.cloud.service.ServiceRequest;

public class SessionStartRequest extends ServiceRequest {
    public static final String KIND = "sessionStartRequest";

    public String user;
    public String appDefinition;
    public String workspaceName;

    public SessionStartRequest() {
	super(KIND);
    }

    public SessionStartRequest(String appId, String user, String appDefinition, String workspaceName) {
	super(KIND, appId);
	this.user = user;
	this.appDefinition = appDefinition;
	this.workspaceName = workspaceName;
    }

    public SessionStartRequest(String appId, String user, String appDefinition) {
	super(KIND, appId);
	this.user = user;
	this.appDefinition = appDefinition;
    }

    public boolean isEphemeral() {
	return this.workspaceName == null || this.workspaceName.isBlank();
    }

    @Override
    public String toString() {
	return "SessionStartRequest [user=" + user + ", appDefinition=" + appDefinition + ", workspaceName="
		+ workspaceName + ", appId=" + appId + ", kind=" + kind + "]";
    }

}