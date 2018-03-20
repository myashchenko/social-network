FROM java:8
VOLUME /tmp
ADD sn-auth-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /sn-auth.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]