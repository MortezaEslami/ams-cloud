FROM openjdk:8-jdk-alpine

COPY target/*0.jar /namingserver.jar

EXPOSE 8761

ENTRYPOINT java  -jar /namingserver.jar
