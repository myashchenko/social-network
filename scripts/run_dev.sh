#!/bin/bash

random_str() {
    openssl rand -base64 32
}

export USERS_DB_POSTGRES_USER=$(random_str)
export USERS_DB_POSTGRES_PASSWORD=$(random_str)

export SN_STORAGE_SERVICE_PASSWORD=$(random_str)
export SN_USER_SERVICE_PASSWORD=$(random_str)
export SN_USER_QUERY_SERVICE_PASSWORD=$(random_str)

docker-compose up