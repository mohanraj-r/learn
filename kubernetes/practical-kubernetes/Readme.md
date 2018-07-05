# Practical Kubernetes

- [Practical Kubernetes](#practical-kubernetes)
	- [Intro](#intro)
	- [Components](#components)
	- [Pods](#pods)
		- [Multiple containers](#multiple-containers)
		- [Health checks](#health-checks)
	- [Replica Sets](#replica-sets)
	- [Service](#service)
	- [Deployment](#deployment)

## Intro
* Slides - [The DevOps 2.3 Toolkit: Kubernetes Workshop](http://vfarcic.github.io/devops23/workshop-packt.html)
	* [vfarcic.github.io/devops23 at master · vfarcic/vfarcic.github.io](https://github.com/vfarcic/vfarcic.github.io/tree/master/devops23)
	* using [hakimel/reveal.js: The HTML Presentation Framework](https://github.com/hakimel/reveal.js/)
* Kubernetes is a container scheduler
* Vs Docker Swarm
	* Swarm is simpler
	* easier to deploy and manage
* `minikube start --vm-driver virtualbox --cpus 3 --memory 3072`
	* to start a cluster
	* `minikube stop|delete`
* `minikube status`
* `minikube dashboard` - to open UI in web browser
* `minikube ssh` - to ssh into the kubernetes
	* `docker ps` - to see docker containers in the node
* `kubectl get nodes` - get list of nodes in cluster
	* `kubectl version --output=yaml`


## Components
* API Server
	* receives commands from `kubectl`
	* sends commands to scheduler
* Scheduler
	* assigns pods to nodes
	* manual overriding scheduling is possible, but not recommended
* Kubelet
	* every node has a kubelet
	* watches for pod assignments
	* in charge of running containers
* Docker / container engine

## Pods
* Basic building blocks
* Collection of containers
	* All containers in a pod are run in the same node
* `kubectl get pods`
	* `kubectl get pods -o yaml` - all info in yaml
* `kubectl run <name> --image <docker-image>`
	* e.g. `kubectl run db --image mongo`
* `kubectl delete deployment <name>`
* Better way is to describe pods in yaml file. e.g. [db.yml](./resources/k8s-specs/pod/db.yml)
	* `kubectl create -f pod/db.yml`
* `kubectl describe pod <name>`
	* lists events on the pod
	* can also use the yaml file used to create the pod
		* `kubectl describe -f pod/db.yml`
* `kubectl exec <pod> <cmd>`
	* execute command in pod
	* e.g. `kubectl exec db ps aux`
	* `kubectl exec -it db sh` - to get to interactive terminal
* If container exits abnormally, if the main process gets killed ..
	* it will be recreated / restarted
* `kubectl delete -f pod/db.yml`
	* Delete pods specified in the yaml file

### Multiple containers
* `kubectl create -f pod/go-demo-2.yml`
* `kubectl exec -c <container name> ...`
	* to run commands in a specific container
* `kubectl logs -c <container name> <pod name>`

### Health checks
* `livenessProbe` in pod yaml file describes health checks
	* e.g. [go-demo-2-health.yml](./resources/k8s-specs/pod/go-demo-2-health.yml)
* when health checks fails, containers are restarted


## Replica Sets
* allows to scale pods up, down
* e.g. [go-demo-2.yml](./resources/k8s-specs/rs/go-demo-2.yml)
	```yaml
	spec:
	replicas: 2
	selector:
		matchLabels:
		type: backend
		service: go-demo-2
	```
	* Creates n (2) pods as specified in `replicas`
		* replicates using `selector`
* `kubectl create -f rs/go-demo-2.yml`
* `kubectl get rs`
	* list replicasets
* `kubectl delete -f rs/go-demo-2.yml`
	* optionally specifying `--cascade=false` will delete the rs (replicasets)
		* but leave the pods created
* `kubectl apply -f ..`
	* to apply and ensure rs
* `kubectl get pods --show-labels`
	* shows lables of pods
* changing labels of running pods using `kubectl label ..`
	* will kill pods that don't match the rs
	* and create pods as necessary to match the rs spec
* [StatefulSets - Kubernetes](https://kubernetes.io/docs/concepts/workloads/controllers/statefulset/)
	* > Manages the deployment and scaling of a set of Pods , and provides guarantees about the ordering and uniqueness of these Pods.


## Service
* Abstraction above replicasets
* Routing layer
	* enable communication between pods
	* enable communication from outside cluster
* Endpoint controller watches for service events
	* creates endpoint objects
	* Kubeproxy
		* adds iptable rules
		* ensures services can reach one another
		* KubeDNS manages DNS entries
* Exposing ports
	* Service allows accessing container ports from outside
	* Unless a service is defined ports are all internal
		* not accessible from outside
	* Kubeproxy allows to access the services running on randomly assigned ports
* [svc/go-demo-2.yml](./resources/k8s-specs/svc/go-demo-2.yml)
	* Not a good practise to have fixed ports in the spec
	* Better to let kubernetes choose one randomly
* `kubectl create -f svc/go-demo-2.yml`
* `kubectl get services`
	* list services
* `kubectl get all`
	* lists all services, rs, pods
* `kubectl delete service|rc|pods --all`

## Deployment
* Abstraction above services
* [deploy/go-demo-2.yml](./resources/k8s-specs/deploy/go-demo-2-db.yml)
	* `kind: Deployment`
		* describes a deployment
* `kubectal create -f ...`
	* to create a deployment
* `kubectl set image ...`
	* to update the container image
	* Creates a new rs with the new image
		* scales down the existing rs with older image
		* as new container become up and ready in the new rs
* `kubectl rollout ..`
	* to do a rolling update
* `kubectl scale deployment ...`
	* to change params of deployed cluster (e.g. replicas) without changing the yaml file
