FROM openjdk:10.0.1

MAINTAINER Mykola Yashchenko <vkont4@gmail.com>

VOLUME /tmp

ADD sn-discovery-service-0.0.1-SNAPSHOT.jar app.jar

RUN bash -c 'touch /app.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]