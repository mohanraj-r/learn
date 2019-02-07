# Istio on Kubernetes

## Resources

- [redhat-developer-demos/istio-tutorial: Istio Tutorial for Java Microservices](https://github.com/redhat-developer-demos/istio-tutorial)
  - [istio-tutorial/1setup.adoc at master · redhat-developer-demos/istio-tutorial](https://github.com/redhat-developer-demos/istio-tutorial/blob/master/documentation/modules/ROOT/pages/1setup.adoc)
- [burrsutter/scripts-istio: My bash shell scripts for easy demo'ing of bit.ly/istio-tutorial](https://github.com/burrsutter/scripts-istio)
- [Istio on Kubernetes - Google Slides](https://docs.google.com/presentation/d/1AF0ZrzkesxRpgZciZeTq9O-YHuO85R1NxsFofak-je4)
- [Extend the Kubernetes API with CustomResourceDefinitions - Kubernetes](https://kubernetes.io/docs/tasks/access-kubernetes-api/custom-resources/custom-resource-definitions/)
  - Custom object types in k8s that is used by/with `1istio

### GKE

- [Istio on GKE  |  Istio on GKE  |  Google Cloud](https://cloud.google.com/istio/docs/istio-on-gke/overview)
  - [Installing Istio on Kubernetes Engine  |  Kubernetes Engine Tutorials  |  Google Cloud](https://cloud.google.com/kubernetes-engine/docs/tutorials/installing-istio)
- [Kiali - Service mesh observability dashboard](https://www.kiali.io/)
- [Automating Canary Analysis on Google Kubernetes Engine with Spinnaker  |  Solutions  |  Google Cloud](https://cloud.google.com/solutions/automated-canary-analysis-kubernetes-engine-spinnaker)

### Issues

- [Error starting the VM: error validating certificates for host (changed IP address) · Issue #2952 · minishift/minishift](https://github.com/minishift/minishift/issues/2952)
  - [Creating VirtualBox machine no longer works after rc4 · Issue #2136 · docker/machine](https://github.com/docker/machine/issues/2136)
  - `minishift start --host-only-cidr <net created with docker-machine>`

## Intro

### Why ?

- [Fallacies of distributed computing - Wikipedia](https://en.wikipedia.org/wiki/Fallacies_of_distributed_computing)

### How ?

- Envoy is the man in the middle
  - routes through all traffic
- A k8s pod can run multiple containers
  - not typically done before
- works with http1.1, http2, grpc

### Control plane

- Istio pilot
  - controller that routes between different pods
- Istio Mixer
  - telemetry - gatherint metrics
  - policy - ACL, quota, rate limit enforcement
- Istio is the control plane containing the controller bits
  - Envoy is the data plane proxy
    - that is deployed as a side-car along with the app
  - Istio is used to manage the Envoys

## Traffic control

- Fine grained traffic control than default kubernetes
  - e.g. x% of traffic routed in a different way than just 50-50 in kubernetes
- Blue green deployment
  - diff from A/B testing
  - deploy new build, route traffic
    - keep the old build around, if things go wrong fallback to old build
  - requires twice the amount of resources
- Canary deployment
  - only a subset of traffic is routed to the new build
    - the % is increased gradually if things are going well
- Can also be routed differently based on http attrs
  - e.g. header, user agent etc
- Mirroring traffic
  - to the new build to know about potential issues before its deployed for users
- Outlier detection
  - Detect instances performing badly or with errors and route traffic off of them

### Ref

- [Simple Route Rules :: Istio Tutorial Docs](https://redhat-developer-demos.github.io/istio-tutorial/istio-tutorial/1.0.x/4simple-routerules.html)
- [Advanced Route Rules :: Istio Tutorial Docs](https://redhat-developer-demos.github.io/istio-tutorial/istio-tutorial/1.0.x/4advanced-routerules.html)
- [Service Resiliency :: Istio Tutorial Docs](https://redhat-developer-demos.github.io/istio-tutorial/istio-tutorial/1.0.x/5circuit-breaker.html)
- [Chaos Testing :: Istio Tutorial Docs](https://redhat-developer-demos.github.io/istio-tutorial/istio-tutorial/1.0.x/6fault-injection.html)
- [Policy :: Istio Tutorial Docs](https://redhat-developer-demos.github.io/istio-tutorial/istio-tutorial/1.0.x/7policy.html)

## Security

- Egress
  - by default istio blocks outgoing traffic
- mtls between pods
  - so traffic between pods/services are not visible in plain to istio (e.g tcpdump on istio controller host)
- JSON web token auth
- Access control
- Role-based Access control
  - more sophisticated than plain ACLs

### Ref

- [Egress :: Istio Tutorial Docs](https://redhat-developer-demos.github.io/istio-tutorial/istio-tutorial/1.0.x/8egress.html)
