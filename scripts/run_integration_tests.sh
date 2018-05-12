#!/bin/bash

envType=$1
clusterHost=$2

if [[ "$envType" == "AWS" || "$envType" == "Docker" ]] ; then
    if [[ "$envType" == "AWS" && "$clusterHost" == "" ]] ; then
        echo "For AWS env type clusterHost should be provided."
    else
        ./gradlew sn-integration-tests:test --debug -PintegrationTests -DenvType=${envType} -DclusterHost=${clusterHost}
    fi
else
    echo "Incorrect environment type. Should be either AWS or Docker."
fi