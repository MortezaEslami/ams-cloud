FROM openjdk:8-jdk-alpine

COPY target/*0.jar /ams.jar

ENV PROFILE=Production

ENTRYPOINT java -Dspring.profiles.active=$PROFILE  -jar /ams.jar
