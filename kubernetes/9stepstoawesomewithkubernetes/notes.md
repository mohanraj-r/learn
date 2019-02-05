## Resources

- [burrsutter/9stepsawesome: The exercises/samples repo for bit.ly/9stepsawesome presentation](https://github.com/burrsutter/9stepsawesome)
- [9 Steps to Awesome with Kubernetes by Burr Sutter - YouTube](https://www.youtube.com/watch?v=ZpbXSdzp_vo)
- [GoogleContainerTools/kaniko: Build Container Images In Kubernetes](https://github.com/GoogleContainerTools/kaniko)
- Logs
  - [wercker/stern: ⎈ Multi pod and container log tailing for Kubernetes](https://github.com/wercker/stern)
  - [boz/kail: kubernetes log viewer](https://github.com/boz/kail)
- [Migrating to Microservice Databases](https://learning.oreilly.com/library/view/migrating-to-microservice/9781492048824/)
  - how to deal with DB schema changes with A/B testing, green/blue deployments

## Intro

- ephemeral storage by default
- shared resources for containers in a pod
  - life-cycle
  - network
  - storage
- Labels
  - important way to manage containers
- make docker command use the docker daemon with k8s
  - `eval $(minikube docker-env)`
  - After this the `docker ..` commands work with k8s docker containers

## Health checks

- `readinessProbe` for health check of a service
  - for kubernetes to know which endpoint to probe to know that the service is up and ready
- `livenessProbe` to know if service is up
