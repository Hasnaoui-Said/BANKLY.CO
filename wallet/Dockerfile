FROM openjdk:11

WORKDIR /wallet

COPY target/wallet-0.0.1-SNAPSHOT.jar wallet/

EXPOSE 9091

CMD ["java","-jar","wallet/wallet-0.0.1-SNAPSHOT.jar","--server.port=9091"]