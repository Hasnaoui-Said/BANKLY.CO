FROM openjdk:11

WORKDIR /mic-users

COPY target/mic-users-0.0.1-SNAPSHOT.jar mic-users/

EXPOSE 9090

CMD ["java","-jar","mic-users/mic-users-0.0.1-SNAPSHOT.jar"]