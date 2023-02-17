FROM openjdk:11

WORKDIR /users

COPY target/users-0.0.1-SNAPSHOT.jar users/

EXPOSE 9090

CMD ["java","-jar","users/users-0.0.1-SNAPSHOT.jar","--server.port=9090"]

