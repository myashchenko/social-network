FROM openjdk:10.0.1

MAINTAINER Mykola Yashchenko <vkont4@gmail.com>

ARG JAR_FILE
ADD ${JAR_FILE} app.jar

RUN bash -c 'touch /app.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]