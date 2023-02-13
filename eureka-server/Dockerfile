FROM openjdk:11

WORKDIR /eureka-server

COPY target/eureka-server-0.0.1-SNAPSHOT.jar eureka-server/

EXPOSE 9999

CMD ["java","-jar","eureka-server/eureka-server-0.0.1-SNAPSHOT.jar"]