# multiprojekt
<i>Springboot with Kafka and Cassandra</i>

This is a spring boot application with two modules. <br>
<b>Fe module</b>: This module is responsible for displaying the view and sending messages from the view to the Kafka topic. <br>
<b>BE module</b>: This module reads data from the kafka topic and saves it in the cassandra database

## Kafka in local
cd extras/kafka <br>
docker-compose up

After that the docker containers start, in Kafka CLI: <br>
cd opt/kafka/bin/

Create the topic: <br>
kafka-topics.sh --create --zookeeper zookeeper:2181 --topic messages --partitions 1 --replication-factor 1

After that you can start the app and see the result on http://localhost:8080/.

## Deployment in Kubernetes
I try to create kubernetes deployment for Kafka and Cassandra, with this page:
https://kubernetes.io/docs/tasks/configure-pod-container/translate-compose-kubernetes/

You can see these files in the directory of extras/cassandra and extras/kafka. Unfortunatelly it did not work.

Finally I use helm charts to deploy these programs.

### Deploying Kafka on Kubernetes with helm chart
kubectl create namespace ildidemo <br>
helm repo add bitnami https://charts.bitnami.com/bitnami <br>
helm install kaffka --namespace ildidemo bitnami/kafka

### Deploying Cassandra on Kubernetes with helm chart
helm install cassandra --namespace ildidemo --set dbUser.password=cassandra,cluster.datacenter=datacenter1 bitnami/cassandra

### Deploying Springboot application on Kubernetes (I used Rancher to Kubernetes)
<p/>
1. After clean install build docker image:
<br>docker build -t multiprojekt .
<p/>
3. Create a tag to a registry:
<br>  docker tag multiprojekt registry.k8s.dev.pnt.hu/romanildiko/multiprojekt/multiprojekt
<p/>
4. Push the image to the registry:
<br>docker push registry.k8s.dev.pnt.hu/romanildiko/multiprojekt/multiprojekt
<p/>
5. Deployment:
<br>kubectl apply -f deployment.yaml -n ildidemo
<p/>
6. You can see the result, is you make a port forward:
<br>kubectl port-forward --namespace ildidemo service/multiprojekt :8080
   Here, on the first link.

### Creating Deployment.yaml (before 4.)
In the directory of multiprojekt: <br>
kubectl create deployment multiprojekt --image=registry.k8s.dev.pnt.hu/romanildiko/multiprojekt/multiprojekt --dry-run=client -o=yaml > deployment.yaml <br>
echo --- >> deployment.yaml <br>
kubectl create service loadbalancer multiprojekt --tcp=8080:8080 --dry-run=client -o=yaml >> deployment.yaml

### Create configmap:
kubectl create configmap multiprojekt -n ildidemo --from-file=./k8s/configMaps.yaml

Add to volume in the deployment.yaml with the help of the next page:
https://spring.io/guides/topicals/spring-on-kubernetes/












