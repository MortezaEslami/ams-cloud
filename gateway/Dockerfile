FROM openjdk:8-jdk-alpine

COPY target/*0.jar /gateway.jar

EXPOSE 8762

ENTRYPOINT java -jar /gateway.jar
