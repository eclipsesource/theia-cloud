/* tslint:disable */
/* eslint-disable */
/**
 * Theia.cloud API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 0.8.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { exists } from '../runtime';
/**
 * Request to create a new workspace.
 * @export
 * @interface WorkspaceCreationRequest
 */
export interface WorkspaceCreationRequest {
    /**
     * The App Id of this Theia.cloud instance. Request without a matching Id will be denied.
     * @type {string}
     * @memberof WorkspaceCreationRequest
     */
    appId: string;
    /**
     * The user identification, usually the email address.
     * @type {string}
     * @memberof WorkspaceCreationRequest
     */
    user: string;
    /**
     * The app this workspace will be used with.
     * @type {string}
     * @memberof WorkspaceCreationRequest
     */
    appDefinition?: string;
    /**
     * The label of the workspace
     * @type {string}
     * @memberof WorkspaceCreationRequest
     */
    label?: string;
}

/**
 * Check if a given object implements the WorkspaceCreationRequest interface.
 */
export function instanceOfWorkspaceCreationRequest(value: object): boolean {
    let isInstance = true;
    isInstance = isInstance && "appId" in value;
    isInstance = isInstance && "user" in value;

    return isInstance;
}

export function WorkspaceCreationRequestFromJSON(json: any): WorkspaceCreationRequest {
    return WorkspaceCreationRequestFromJSONTyped(json, false);
}

export function WorkspaceCreationRequestFromJSONTyped(json: any, ignoreDiscriminator: boolean): WorkspaceCreationRequest {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'appId': json['appId'],
        'user': json['user'],
        'appDefinition': !exists(json, 'appDefinition') ? undefined : json['appDefinition'],
        'label': !exists(json, 'label') ? undefined : json['label'],
    };
}

export function WorkspaceCreationRequestToJSON(value?: WorkspaceCreationRequest | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'appId': value.appId,
        'user': value.user,
        'appDefinition': value.appDefinition,
        'label': value.label,
    };
}

