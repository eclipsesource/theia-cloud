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
package org.eclipse.theia.cloud.service;

import static org.eclipse.theia.cloud.common.util.LogMessageUtil.formatLogMessage;
import static org.eclipse.theia.cloud.common.util.LogMessageUtil.generateCorrelationId;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jboss.logging.Logger;

import io.quarkus.security.identity.SecurityIdentity;

@Path("/service")
public class SessionResource {

    private static final Logger LOGGER = Logger.getLogger(SessionResource.class);

    private static final String THEIA_CLOUD_APP_ID = "theia.cloud.app.id";
    private static final String THEIA_CLOUD_USE_KEYCLOAK = "theia.cloud.use.keycloak";
    private static final String INIT = "INIT";
    @Inject
    SecurityIdentity identity;

    private String appId;
    private boolean useKeycloak;

    public SessionResource() {
	appId = System.getProperty(THEIA_CLOUD_APP_ID, "");
	useKeycloak = Boolean.valueOf(System.getProperty(THEIA_CLOUD_USE_KEYCLOAK, "false"));
	LOGGER.info(formatLogMessage(INIT, "App Id: " + appId));
    }

    @POST
    public Reply launchSession(Session session) {
	String correlationId = generateCorrelationId();
	LOGGER.info(formatLogMessage(correlationId,
		"useKeycloak " + useKeycloak + " ; isAnonymous: " + identity.isAnonymous()));
	if (useKeycloak && identity.isAnonymous()) {
	    LOGGER.info(formatLogMessage(correlationId, "Launching session call without authentication."));
	    return new Reply(false, "", "Unauthenticated");
	}
	if (wrongAppId(session)) {
	    LOGGER.info(
		    formatLogMessage(correlationId, "Launching session call without matching appId: " + session.appId));
	    return new Reply(false, "", "AppId is not matching.");
	}
	LOGGER.info(formatLogMessage(correlationId, "Launching session " + session));
	return K8sUtil.launchSession(correlationId, generateSessionName(session), session.appDefinition, session.user);
    }

    private boolean wrongAppId(Session session) {
	return !appId.equals(session.appId);
    }

    private static String generateSessionName(Session session) {
	return ("ws-" + session.appDefinition + "-" + session.user).replace("@", "at").toLowerCase();
    }
}
