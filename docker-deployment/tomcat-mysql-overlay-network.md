In deploy-using-bridge-network lesson we provided a short guide on how you can
connect Docker containers residing on the same host.
But what if you want to create networks that span multiple hosts? 
Well, since Docker 1.9.0, you can do just that!

So far, we’ve been using the bridge network driver, which has a local scope, 
meaning bridge networks are local to the Docker host. 
Docker now provides a new overlay network driver, which has global scope, 
meaning overlay networks can exist across multiple Docker hosts. 

Unlike bridge networks, overlay networks require some pre-existing conditions before you can create one. These conditions are:

1.Access to a key-value store. Docker supports Consul, Etcd, and ZooKeeper (Distributed store) key-value stores.
2.A cluster of hosts with connectivity to the key-value store.
3.A properly configured Engine daemon on each host in the cluster.
4.Hosts within the cluster must have unique hostnames because the key-value store uses the hostnames to identify cluster members.

Though Docker Machine and Docker Swarm are not mandatory to experience Docker multi-host networking, 
this example uses them to illustrate how they are integrated. 
You’ll use Machine to create both the key-value store server and the host cluster. This example creates a Swarm cluster.

**Prerequisites!!!**
Before you begin, make sure you have a system on your network with the latest version of Docker Engine and Docker Machine installed. The example also relies on VirtualBox.

So what we have by the end of tutorial:
-3 VMs connected to overlay network:
        *1 VM with docker container "tomcat-app", this VM also acts as cluster master 
        *1 VM with docker container "mysqlserver"
        *1 VM with Consul key-value storage needed for coordination of 2 above VMs in the cluster
      
**Step 1**.Set up a key-value store
An overlay network requires a key-value store. The key-value store holds information about the network state which includes discovery, networks, endpoints, IP addresses, and more. Docker supports Consul, Etcd, and ZooKeeper key-value stores. This example uses Consul.

1.Provision a VirtualBox machine called mh-keystore
```
$ docker-machine create -d virtualbox mh-keystore
```
When you provision a new machine, the process adds Docker Engine to the host. This means rather than installing Consul manually, you can create an instance using the consul image from Docker Hub. You’ll do this in the next step.

2.Set your local environment to the mh-keystore machine, e.g. your docker client now points to mh-keystore docker host.
```
$  eval "$(docker-machine env mh-keystore)"
```

3.Start a progrium/consul (docker Consul image) container running on the mh-keystore machine.
```
$ docker run -d -p 8500:8500 -h consul progrium/consul -server -bootstrap
```
The client starts a progrium/consul image running in the mh-keystore machine. The server is called consul and is listening on port 8500.
Keep your terminal open and move onto the next step.

**Step 2**. Create a Swarm cluster
In this step, you use docker-machine to provision the hosts for your network. At this point, you won’t actually create the network. You’ll create several machines in VirtualBox. One of the machines will act as the Swarm master; you’ll create that first. As you create each host, you’ll pass the Engine options on that machine that are needed by the overlay network driver.

1.Create a Swarm master.
```
$ docker-machine create -d virtualbox --swarm --swarm-master \
--swarm-discovery="consul://$(docker-machine ip mh-keystore):8500" \
--engine-opt="cluster-store=consul://$(docker-machine ip mh-keystore):8500" \
--engine-opt="cluster-advertise=eth1:2376" master-host
```
At creation time, you supply the Engine daemon with the --cluster-store option. This option tells the Engine the location of the key-value store for the overlay network. The bash expansion $(docker-machine ip mh-keystore) resolves to the IP address of the Consul server you created in “STEP 1”. The --cluster-advertise option advertises the machine on the network.

2.Create another host and add it to the Swarm cluster.
```
docker-machine create -d virtualbox --swarm \
--swarm-discovery="consul://$(docker-machine ip mh-keystore):8500" \
--engine-opt="cluster-store=consul://$(docker-machine ip mh-keystore):8500" \
--engine-opt="cluster-advertise=eth1:2376" worker-host
```
List your machines to confirm they are all up and running.
```
$ docker-machine ls
 NAME          ACTIVE   DRIVER       STATE     URL                         SWARM                  DOCKER    ERRORS
 master-host   -        virtualbox   Running   tcp://192.168.99.101:2376   master-host (master)   v1.11.2   
 mh-keystore   -        virtualbox   Running   tcp://192.168.99.100:2376                          v1.11.2   
 worker-host   -        virtualbox   Running   tcp://192.168.99.102:2376   master-host            v1.11.2 
```
At this point you have a set of hosts running on your network. You are ready to create a multi-host network for containers using these hosts.
Open another terminal.

**Step 3**. Create the overlay Network

1.Set your docker environment to the Swarm master.
```
$ eval $(docker-machine env --swarm master-host)
```
Using the --swarm flag with docker-machine restricts the docker commands to Swarm information alone.

2.Use the docker info command to view the Swarm.
```
$ docker info
Containers: 3
 Running: 3
 Paused: 0
 Stopped: 0
Images: 2
Server Version: swarm/1.2.3
Role: primary
Strategy: spread
Filters: health, port, containerslots, dependency, affinity, constraint
Nodes: 2
 master-host: 192.168.99.101:2376
  └ ID: QOOR:NAIM:J5KP:M5UC:543Y:T7H2:KJGX:OCGL:QZA6:KB2N:WDG2:5YVS
  └ Status: Healthy
  └ Containers: 2
  └ Reserved CPUs: 0 / 1
  └ Reserved Memory: 0 B / 1.021 GiB
  └ Labels: executiondriver=, kernelversion=4.4.12-boot2docker, operatingsystem=Boot2Docker 1.11.2 (TCL 7.1); HEAD : a6645c3 - Wed Jun  1 22:59:51 UTC 2016, provider=virtualbox, storagedriver=aufs
  └ UpdatedAt: 2016-06-26T10:14:43Z
  └ ServerVersion: 1.11.2
 worker-host: 192.168.99.102:2376
  └ ID: WFJF:AKE2:6VNC:U2AM:U33C:B466:ZDGE:W3BI:GM66:EW45:RUK4:WWOA
  └ Status: Healthy
  └ Containers: 1
  └ Reserved CPUs: 0 / 1
  └ Reserved Memory: 0 B / 1.021 GiB
  └ Labels: executiondriver=, kernelversion=4.4.12-boot2docker, operatingsystem=Boot2Docker 1.11.2 (TCL 7.1); HEAD : a6645c3 - Wed Jun  1 22:59:51 UTC 2016, provider=virtualbox, storagedriver=aufs
  └ UpdatedAt: 2016-06-26T10:14:42Z
  └ ServerVersion: 1.11.2
```   
From this information, you can see that you are running three containers and two images on the Master.

3.Create your overlay network.
```
$ docker network create --driver overlay --subnet=10.0.9.0/24 my-net
```
You only need to create the network on a single host in the cluster. In this case, you used the Swarm master but you could easily have run it on any host in the cluster.

Check that network is running:
```
$ docker network ls
NETWORK ID          NAME                 DRIVER
4c1e5b41c3a1        master-host/bridge   bridge              
df169ae51bfb        master-host/host     host                
2253c008cbf6        master-host/none     null                
**b8a3d5d64718**        **my-mh-net**            overlay             
0ee8ce6469a0        worker-host/bridge   bridge              
1992dc20124d        worker-host/host     host                
073c6f4ddace        worker-host/none     null
```
As you are in the Swarm master environment, you see all the networks on all the Swarm agents: the default networks on each engine and the single overlay network.

4.Switch to each another Swarm host in turn and list the networks.
```
$ eval $(docker-machine env worker-host)
$ docker network ls
NETWORK ID          NAME                DRIVER
0ee8ce6469a0        bridge              bridge              
1992dc20124d        host                host                
b8a3d5d64718        my-mh-net           overlay             
073c6f4ddace        none                null  
```
As you see my-mh-net has b8a3d5d64718 id. You now have a multi-host container network running!

**Step 4**. Access "tomcat-app" container from outside the cluster.

1.Go to master host:
```
$ eval $(docker-machine env --swarm master-host)
$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                           NAMES
831ffbcf0e6e        docam/tomcat        "bash"                   About an hour ago   Up About an hour    192.168.99.101:8080->8080/tcp   master-host/tomcat-app
702db6d749c2        docam/mysql-cont    "docker-entrypoint.sh"   About an hour ago   Up About an hour    3306/tcp                        worker-host/mysqlserver
```
Here we see the actual outer IP of tomcat cluster.

2.Access that container:
```
$ curl 192.168.99.101:8080/rest-service-provider-1.0-SNAPSHOT/car/1
{"carId":1,"carName":"Cruiser","dateOfCreation":"14/10/2015","producer":{"producerId":1,"producerName":"toyota","country":null,"countOfCars":0},"picture":null,"base64EncodedPicture":"Nothing to display"}
```
Yep! Here we go!

Also check if mysqlserver is accessible from tomcat cluster:
```
$ root@831ffbcf0e6e:/usr/local/tomcat# ping mysqlserver
  PING mysqlserver (10.0.9.3): 56 data bytes
  64 bytes from 10.0.9.3: icmp_seq=0 ttl=64 time=0.347 ms
  64 bytes from 10.0.9.3: icmp_seq=1 ttl=64 time=0.421 ms
  ^C--- mysqlserver ping statistics ---
  2 packets transmitted, 2 packets received, 0% packet loss
  round-trip min/avg/max/stddev = 0.347/0.384/0.421/0.037 ms

```

Materials used:
https://docs.docker.com/engine/userguide/networking/get-started-overlay/
