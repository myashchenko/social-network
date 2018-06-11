#!/bin/bash

./gradlew sn-discovery-service:docker \
          sn-gateway-service:docker \
          sn-monitor-dashboard:docker \
          sn-user:sn-auth-service:docker \
          sn-user:sn-user-query-service:docker \
          sn-user:sn-user-service:docker \
          sn-notification-service:docker
