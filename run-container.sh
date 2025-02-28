#!/bin/bash

if [ "$1" == "podman" ]; then
    docker="podman"
elif [ "$1" == "podman-remote" ]; then # for podman on MacOS or WSL
    docker="podman-remote-static-linux_amd64"
else
    docker="docker"
fi

$docker build -t lso-client . && \
#$docker run -it --rm --name running-lso-client lso-client
$docker run -it --network=host --rm --name running-lso-client lso-client
