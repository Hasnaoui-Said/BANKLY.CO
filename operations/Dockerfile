FROM openjdk:11

WORKDIR /operations

COPY target/operations-0.0.1-SNAPSHOT.jar operations/

EXPOSE  9092

CMD ["java","-jar","operations/operations-0.0.1-SNAPSHOT.jar", "--server.port=9092"]
