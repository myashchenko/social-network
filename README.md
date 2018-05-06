# Social network

[![Build Status](https://travis-ci.org/YashchenkoN/social-network.svg?branch=master)](https://travis-ci.org/YashchenkoN/social-network)
[![codebeat badge](https://codebeat.co/badges/0e01df9f-245c-4757-aac2-826753b8f480)](https://codebeat.co/projects/github-com-yashchenkon-social-network-master)
[![Dependency Status](https://www.versioneye.com/user/projects/5aef02250fb24f5469670132/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5aef02250fb24f5469670132)

Social Network - is a project aimed to develop high-scalable, fast and easy to contribute
social network. The purpose of this project is to try to use best practices of
complex systems development.

Project uses the latest version of Java - Java 10 and modern frameworks. Social Network - is a
microservices project, each service is responsible for one thing that allows to scale them
independently.

### Start order

Microservices depend on each other, so the start order should be:

- sn-auth
- sn-discovery-service
- sn-gateway-service
- other services

### Docker

Development might be done using Docker. Each service has a build job to create a docker image.
There is `docker-compose.yml` file that connects all services together. To update and run docker env
please use the following commands:

```
gradle clean buildDocker
docker-compose up
```
