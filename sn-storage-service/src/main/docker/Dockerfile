FROM openjdk:10.0.1

MAINTAINER Mykola Yashchenko <vkont4@gmail.com>

ARG JAR_FILE
ADD ${JAR_FILE} app.jar

RUN bash -c 'mkdir /files'
RUN bash -c 'touch /app.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=development","-jar","/app.jar"]