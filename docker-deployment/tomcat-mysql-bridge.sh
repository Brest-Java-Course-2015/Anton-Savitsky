#!/usr/bin/env bash

sudo docker network create backend

sudo docker run -d --net=backend --name=mysqlserver -e MYSQL_ROOT_PASSWORD=pswd docam/mysql

sudo docker run -d --net=backend -p 8080:8080 --name=tomcat-app docam/tomcat
