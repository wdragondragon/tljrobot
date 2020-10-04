#!/bin/bash
#构建昊方服务的docker shell

NETWORK=haofang-service

DOCKER_CONTAINER=robot-app
DOCKER_IMG=robot-images
SERVER_PORT=5700
APP_LOG=/log
HOST_LOG=/var/haofangproject/haofangServer/log

docker stop ${DOCKER_CONTAINER}
docker rm ${DOCKER_CONTAINER}
docker rmi ${DOCKER_IMG}
docker build -t ${DOCKER_IMG} .
#docker run --rm -it -p ${SERVER_PORT}:${SERVER_PORT} -v ${HOST_LOG}:${APP_LOG} --link ${MYSQL_CONTAINER}:${NETWORK} --name ${DOCKER_CONTAINER} ${DOCKER_IMG}
docker run --rm -it -d --tty -p ${SERVER_PORT}:${SERVER_PORT}  --network ${NETWORK} --name ${DOCKER_CONTAINER} ${DOCKER_IMG}


