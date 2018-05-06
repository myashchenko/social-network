FROM openjdk:10.0.1

VOLUME /tmp

ADD sn-storage-service-0.0.1-SNAPSHOT.jar app.jar

RUN bash -c 'mkdir /files'
RUN bash -c 'touch /app.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=development","-jar","/app.jar"]