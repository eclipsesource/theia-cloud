# Getting started with Minikube

Minikube is a local Kubernetes that allows you to test and develop for Kubernetes. 

## Install minikube

If minikube is not installed on your system, go to https://minikube.sigs.k8s.io/docs/start/ and follow the instructions in Step 1 Installation.

## Start Minikube

Create a new Minikube cluster using the `minikube start` command. Please adjust the `/home/user/tmp/minikube` part of the start command to point to a directory on your machine.\
`minikube start --addons=ingress --vm=true --memory=4096 --cpus=2 --mount-string="/home/user/tmp/minikube:/data/test" --mount --mount-gid 101 --mount-uid 101`

Please note: If later on the persisted storage mounted at `/persisted` in the example workspace is not writable for you, this may be to a limitation in minikube. Please try\
`minikube mount /home/user/tmp/minikube:/data/test --uid 101 --gid 101`\
then. You may need to adjust the firewall on your system however.

## Install Prerequisites

Theia.cloud depends on some prerequisite applications to be available on your system or available in the Cluster.

### Helm 3

Follow the steps in https://helm.sh/docs/intro/install/ to install Helm on your system.

### Cert-Manager

Please check https://cert-manager.io/docs/installation/ for the latest installation instructions.

As of writing this guide the installation command looks like this:\
`kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.8.0/cert-manager.yaml`

### NginX Ingress Controller

This is installed in minikube already when started with the `--addons=ingress` argument, so nothing has to be installed for the Minikube Guide.

For other platforms see https://kubernetes.github.io/ingress-nginx/deploy/

### Optional: Keycloak

See [Keycloak](Keycloak.md)

## Install Theia.Cloud

We use the Minikube IP for our hostnames:

```bash
$ minikube ip
192.168.39.81
# for other platforms determine the external nginx ingress controller ip with
# kubectl get services ingress-nginx-controller -n ingress-nginx'

# If you don't use Minikube or your shell does not support $(), replace $(minikube ip) with your IP to install Theia.Cloud

helm install theia-cloud ./helm/theia.cloud --namespace theiacloud --create-namespace --values ./helm/theia.cloud/valuesMinikube.yaml --set hosts.workspace=workspace.$(minikube ip).nip.io --set hosts.landing=theia.cloud.$(minikube ip).nip.io --set hosts.instance=ws.$(minikube ip).nip.io --set keycloak.authUrl=https://keycloak.$(minikube ip).nip.io/

# Optional: switch to the newly created namespace
kubectl config set-context --current --namespace=theiacloud
```

Since we are running in Minikube on our local machine, which is not exposed to the internet, we cannot use letsencrypt certifcates. Installations that are accessible from the internet will create valid letsencrypt certificates.

Browser to below URLs and accept the temporary certificate.

In Chrome:
`Your connection is not private -> Advanced -> Proceed to ...`

In Firefox:
`Warning: Potential Security Risk Ahead -> Advanced... -> Accept the Risk and Continue`

https://workspace.192.168.39.81.nip.io \
https://ws.192.168.39.81.nip.io  \
https://keycloak.192.168.39.81.nip.io/

At last, go to

https://theia.cloud.192.168.39.81.nip.io

and accept the certificate. This will bring you to the Keycloak login page and will start up a workspace for your user. The very first may take a bit longer, since the docker image for the workspace has to be pulled.