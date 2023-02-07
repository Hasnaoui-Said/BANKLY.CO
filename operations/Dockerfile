FROM openjdk:11

WORKDIR /orders

COPY target/demo-0.0.1-SNAPSHOT.jar operations/

EXPOSE 9090

CMD ["java","-jar","operations/demo-0.0.1-SNAPSHOT.jar"]