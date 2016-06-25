This short guideline shows how to connect 2 containers: Tomcat and MySQL using networking, specifically bridge network.

Docker bridge network is basically single-host network. This network allows to connect 2 or more Docker containers on the same host. 

Docker allows to connect containers into user defined networks.
Lets observe list of available networks:
```
$ docker network ls
    NETWORK ID          NAME                DRIVER
    362c9d3713cc        bridge              bridge
    fbd276b0df0a        singlehost          bridge
    591d6ac8b537        none                null
    ac7971601441        host                host
```
Next create new network called "backend":
```
$ docker network create backend
```
Check if that worked out fine:
```
$ docker network ls
NETWORK ID          NAME                DRIVER
362c9d3713cc        bridge              bridge
fbd276b0df0a        singlehost          bridge
591d6ac8b537        none                null
ac7971601441        host                host
d97889cef288        backend             bridge
```
Our backend network was created using default bridge driver.
Now we need to run containers attaching them to this network.
First start up MySQL container and give it name mysqlserver
```
$ docker run -d --net=backend --name=mysqlserver -e MYSQL_ROOT_PASSWORD=pswd docam/mysql

```
Here we gave a name of "mysqlserver" to our container and mentioned a name of "backend" network
which we want our container to be assigned to.

Now this is time for tomcat container "docam/tomcat". We also map port 8080 of a container
to port 8080 of localhost so that it's accessible on _localhost:8080_.
```
$ docker run -d --net=backend -p 8080:8080 --name=tomcat-app docam/tomcat
```
Now we have 2 containers assigned to the same "backend" network.
```
$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
3926002c13c9        docam/tomcat        "catalina.sh run"        8 seconds ago       Up 7 seconds        8080/tcp            tomcat-app
5d7d7c436662        docam/mysql         "docker-entrypoint.sh"   19 minutes ago      Up 19 minutes       3306/tcp            mysqlserver
```
And now our mysqlserver container is accessible from all containers of its network.
More specifically now we can use "mysqlserver" as domain name of the mysqlserver
Docker container.
```
$ curl localhost:8080/rest-service-provider-1.0-SNAPSHOT/car/1
{"carId":1,"carName":"2","dateOfCreation":"13/10/2015","producer":
{"producerId":0,"producerName":"toyota","country":"Korea","countOfCars":0},"picture":null,"base64EncodedPicture":"Nothing to display"}
```