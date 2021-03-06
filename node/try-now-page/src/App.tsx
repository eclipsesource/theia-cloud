import './App.css';

import { getTheiaCloudConfig, startSession } from '@theia-cloud/common';
import { useState } from 'react';

import { Spinner } from './components/Spinner';

function App(): JSX.Element {
  const [config] = useState(() => getTheiaCloudConfig());
  const [error, setError] = useState<string>();
  const [acceptedTerms, setAcceptedTerms] = useState(false);
  const [loading, setLoading] = useState(false);

  if (config === undefined) {
    return (
      <div className='App'>
        <strong>FATAL: Theia.Cloud configuration could not be found.</strong>
      </div>
    );
  }

  document.title = `${config.appName} - Try Now`;

  const handleStartSession = (): void => {
    setLoading(true);
    setError(undefined);
    startSession({
      appId: config.appId,
      serviceUrl: config.serviceUrl,
      appDefinition: config.appDefinition
    })
      .catch((err: Error) => {
        setError(err.message);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  const openTerms = (): boolean => {
    window.open('/terms.html', 'popup', 'width=600,height=600');
    return false;
  };

  return (
    <div className='App'>
      <img src={'/logo.png'} className='App__logo' alt='logo' />
      <p>
        Powered by{' '}
        <a href='http://theia-cloud.io' target='_blank' rel='noreferrer'>
          Theia.Cloud
        </a>
      </p>
      <p>
        <input
          id='accept-terms'
          type='checkbox'
          checked={acceptedTerms}
          onChange={ev => setAcceptedTerms(ev.target.checked)}
        />
        <label htmlFor='accept-terms'>
          I accept the{' '}
          <a target='popup' href='/terms.html' onClick={openTerms}>
            terms and conditions.
          </a>
        </label>
      </p>
      {loading ? (
        <>
          <Spinner />
          <p>We will now spawn a dedicated Blueprint for you, hang in tight, this might take up to 3 minutes.</p>
        </>
      ) : (
        <p>
          <button disabled={!acceptedTerms} onClick={handleStartSession} className='App__try-now-button'>
            Launch {config.appName}
          </button>
        </p>
      )}
      {error !== undefined && (
        <p>
          <strong>ERROR: {error}</strong>
        </p>
      )}
      <p>
        Having problems? Please{' '}
        <a target='_blank' href='https://github.com/eclipsesource/theia-cloud/issues' rel='noreferrer'>
          report an issue
        </a>
        .
      </p>
    </div>
  );
}

export default App;
