# Social network

[![Build Status](https://travis-ci.org/YashchenkoN/social-network.svg?branch=master)](https://travis-ci.org/YashchenkoN/social-network)
[![codebeat badge](https://codebeat.co/badges/0e01df9f-245c-4757-aac2-826753b8f480)](https://codebeat.co/projects/github-com-yashchenkon-social-network-master)
[![codecov](https://codecov.io/gh/YashchenkoN/social-network/branch/master/graph/badge.svg)](https://codecov.io/gh/YashchenkoN/social-network)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/db4b421c651548d48bea74ccc67d3077)](https://www.codacy.com/app/YashchenkoN/social-network?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=YashchenkoN/social-network&amp;utm_campaign=Badge_Grade)

Social Network - is a project aimed to develop high-scalable, fast and easy to contribute
social network. The purpose of this project is to show the best practices of
complex systems development.

Project uses the latest release version of Java - 10 and modern frameworks. Social Network - is a project
 built on top of microservices architecture where each service is responsible for one thing that allows to scale them
independently.

### Start order

Microservices depend on each other, so the start order should be:

- sn-auth-service
- sn-discovery-service
- sn-gateway-service
- other services

### Docker

Development might be done using Docker. Each service has a build job to create a docker image.
There is `docker-compose.yml` file that connects all services together. To update and run docker env
please use the following commands:

```
./gradlew clean build
./scripts/docker_all.sh
docker-compose up
```
