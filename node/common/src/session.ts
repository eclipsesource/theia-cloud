import axios from 'axios';
import { v4 as uuidv4 } from 'uuid';

export interface SessionOptions {
  serviceUrl: string;
  appDefinition: string;
  appId: string;
  user?: string;
}

export function startSession(options: SessionOptions, retries = 0): Promise<void> {
  const { appId, serviceUrl, appDefinition, user = uuidv4() + '@theia.cloud' } = options;
  console.log('Calling to ' + serviceUrl);
  return axios
    .post(
      serviceUrl,
      {
        appDefinition: appDefinition,
        user: user,
        appId: appId
      },
      {
        timeout: 300000
      }
    )
    .then(
      response => {
        if (response.data.success) {
          console.log(`Redirect to: https://${response.data.url}`);
          location.replace(`https://${response.data.url}`);
        } else {
          console.error(response.data.error);
          throw new Error('Sorry, we were unable to launch another Theia Blueprint instance for you at this point since the cluster reached the maximum of available instances. Please try again later, instances are shut down after 30 minutes so there is a good chance that you might be able to grab an instance in 30 minutes. Please also note this is not a technical limit of Theia.cloud, but was intentionally set by us to keep this free offer within its intended budget.');
        }
      },
      error => {
        // Request timed out or returned an error with an error HTTP code.
        console.error(error.message);
        if (retries > 0) {
          startSession(options, retries - 1);
        } else {
          throw error;
        }
      }
    );
}
