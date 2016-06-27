This is a short guideline ob how you can setup Kubernetes cluster locally
on your machine. There are several possibilities to accomplish that.
Info on all available ways: http://kubernetes.io/docs/getting-started-guides/.

We'll use Docker engine to recreate all needed components of Kubernetes cluster.
So you need a Docker engine installed on your machine.

The script setup_kubernetes.sh is aimed to configure all pieces of the system.
Hyperkube is a binary containing all that dependencies.

Such components are:
*etcd key-value storage
*API server (aka master)
*controller manager
*kube proxy
*kubelet daemon
*scheduler etc.

*For a nice explanation of these components please refer to https://deis.com/blog/2016/kubernetes-overview-pt-1/.

So after observing script run it in the shell.
Also in order to access cluster you'll need kubectl cli. Download and install it from https://github.com/kubernetes/kubernetes/releases.
Check if you have any pods running in the cluster.
```
$ kubectl get pods
NAME                           READY     STATUS    RESTARTS   AGE
```
Of course there is nothing for now.

In order to have any apps running in the cluster you may choose:
 1.Run Docker container from DockerHub image right away with the following command:
```
 $ kubectl run example --image=nginx
   deployment "example" created 
```
Here we run http nginx server from Docker image.
 2.Create deployment from configuration file (either .yaml or .json).
```
# mysql-depl.yaml
apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: mysql-depl
spec:
  replicas: 1
  template:
    metadata:
      labels:
        app: mysqlserver
    spec:
      containers:
      - name: mysql
        image: docam/mysql-cont
        ports:
        - containerPort: 3306
        env: 
        - name: "MYSQL_ROOT_PASSWORD"
          value: "pswd"
```
*For the explanation refer to http://kubernetes.io/docs/user-guide/deployments/.
 Run command:
```
$ kubectl create -f mysql-depl.yaml
   deployment created 
```
 Do the same for another pod we want to have running:
```
# tomcat-depl.yaml
apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: tomcat-depl
spec:
  replicas: 1
  template:
    metadata:
      labels:
        app: tomcat-app
    spec:
      containers:
      - name: tomcat
        image: docam/tomcat
        ports:
        - containerPort: 8080
      
```
```
$ kubectl create -f tomcat-depl.yaml
   deployment created 
```
 3.There are also Replication controller and Pod kind of config files which will not discuss here.

Every pod has its own dynamic IP address within the cluster. That's cool, but what if we want to access a pod from another pod or
from outer world? Here Services come into play. Service acts as a gate to a pod or a group of pods each having its own dynamic IP.
Service takes responsibility of managing all incoming request and forwarding them to a particular node.

The easiest way to spin up your service is to write a config file (here in json):
_tomcat-service.json_
```
{
    "kind": "Service",
    "apiVersion": "v1",
    "metadata": {
        "name": "mysql-service"
    },
    "spec": {
        "selector": {
            "app": "mysqlserver"
        },
        "ports": [
            {
                "protocol": "TCP",
                "port": 3366,
                "targetPort": 3306
            }
        ],
        "type": "LoadBalancer",
        "externalIPs" : [
            "10.26.11.12"
        ]
    }
}
```
Notice "selector" object having pair "app": "mysqlserver". This tells a service which pods it serves. The same label must be provided
in config file of each pod you want to be backed up by the service.

We also put External IP of 10.26.11.12 which is our eth0 interface IP meaning local network IP. You may check your IP by typing: _ip addr | grep eth0_ in your shell.
```
$ kubectl create -f tomcat-service.json
```
Check a list of all running services on the cluster:
```
$ kubectl get svc
NAME             CLUSTER-IP   EXTERNAL-IP    PORT(S)    AGE
kubernetes       10.0.0.1     <none>         443/TCP    17d
mysql-service    10.0.0.26    ,10.26.11.12   3366/TCP   51m
```
Here you also have Cluster IP assigned to the service: 10.0.0.26.

So now we may refer to our mysql pods using either Cluster IP (for inner cluster connections) or External IP.
```
$ mysql -u root -h 10.0.0.26 -P 3366 -p
Enter password: 
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 1
Server version: 5.6.31 MySQL Community Server (GPL)
Copyright (c) 2000, 2016, Oracle and/or its affiliates. All rights reserved.
Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.
Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.
mysql> 
```

Do the same for tomcat service.
```
{
    "kind": "Service",
    "apiVersion": "v1",
    "metadata": {
        "name": "tomcat-service"
    },
    "spec": {
        "selector": {
            "app": "tomcat-app"
        },
        "ports": [
            {
                "protocol": "TCP",
                "port": 8091,
                "targetPort": 8080
            }
        ],
        "type": "LoadBalancer",
        "externalIPs" : [
            "10.26.11.12"
        ]
    }
}
```

```
$ kubectl create -f tomcat-service.json
```
```
$ kubectl get svc
NAME             CLUSTER-IP   EXTERNAL-IP    PORT(S)    AGE
kubernetes       10.0.0.1     <none>         443/TCP    17d
mysql-service    10.0.0.26    ,10.26.11.12   3366/TCP   1h
tomcat-service   10.0.0.73    ,10.26.11.12   8091/TCP   1h
```

Check connectivity:
```
$ ping 10.26.11.12
$ ping 10.0.0.73
```

Check if Tomcat is running:
```
$ curl 10.26.11.12:8091
```

Lets rewind. Now we may refer to our pods using services (either internally or externally), but the thing is that even Services can go down
and once they are restarted they are assigned different IPs. So it will be a really big headache to always reconfigure container apps to refer
to a right IP. We certainly need some naming convention so that we refer to a service inside a cluster using some stable name.

This is the time for DNS service. Its possible to spin up one using SkyDNS container. For the details of this service and how to configure it
refer to http://kubernetes.io/docs/user-guide/connecting-applications/#dns and https://github.com/kubernetes/kubernetes/blob/release-1.2/cluster/addons/dns/README.md.
 
Basically what you'll need to do is to run config YAML file which will start all needed pods and DNS service inside the cluster.
```
 $ kubectl create -f skydns-config.yaml 
```
Restart your pods. And now you may check if domain name of services is available in pods.
```
$ docker exec -it id_of_docker_container bash
root # curl tomcat-service:8091
```
