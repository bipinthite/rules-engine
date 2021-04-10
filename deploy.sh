#!/bin/bash

SERVICE_NAME=$1
IMAGE=$2
ENVIRONMENT=$3
PORT=$4
REPLICA=$5

DOCKER_NETWORK_NAME=back-tier

# Logs directory
LOGS_DIR=/var/log/$USER/$SERVICE_NAME
mkdir -p $LOGS_DIR

# Pull the docker image
docker pull ${IMAGE}

# Check if docker network exists or not
if [ ! "$(docker network ls -qf name=${DOCKER_NETWORK_NAME})" ]; then
    # Create docker network named 'back-tier' with overlay driver
    echo "Creating the docker network"
    docker network create --driver overlay ${DOCKER_NETWORK_NAME}
fi

if [ $ENVIRONMENT = "dev" ]; then
	MEMORY=500m
elif [ $ENVIRONMENT = "qa" ]; then
	MEMORY=500m
elif [ $ENVIRONMENT = "staging" ]; then
	MEMORY=500m
elif [ $ENVIRONMENT = "preprod" ]; then
	MEMORY=500m
else
	MEMORY=1g
fi

# Check if docker service exists or not
if [ ! "$(docker service ls -qf name=${SERVICE_NAME})" ]; then
    # Create docker service
    echo "Creating the docker service"
    docker service create --name ${SERVICE_NAME} \
        --env JAVA_OPTS='-Xmx768m -XX:MaxRAM=1g' \
        --env SPRING_PROFILES_ACTIVE=${ENVIRONMENT} \
        --mount type=bind,source=${LOGS_DIR},destination=/logs \
        --hostname="{{.Node.Hostname}}-{{.Service.Name}}" \
        --network ${DOCKER_NETWORK_NAME} \
        --publish ${PORT}:${PORT} \
        --replicas ${REPLICA} \
        --restart-condition on-failure \
        --restart-delay 10s \
        --restart-max-attempts 10 \
        --update-delay 10s \
        --update-parallelism 1 \
        --limit-cpu "0.5" \
        --limit-memory ${MEMORY} \
        --with-registry-auth \
        ${IMAGE}
else
    # Update docker service
    echo "Updating the docker service"
    docker service update \
        --env-add SPRING_PROFILES_ACTIVE=${ENVIRONMENT} \
        --replicas ${REPLICA} \
        --update-delay 10s \
        --update-parallelism 1 \
        --limit-cpu "0.5" \
        --limit-memory ${MEMORY} \
        --with-registry-auth \
        --image ${IMAGE} \
        ${SERVICE_NAME}
fi
