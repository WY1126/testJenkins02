#FROM ubuntu:latest
#LABEL authors="17706"
#
#ENTRYPOINT ["top", "-b"]

FROM openjdk:8-jdk
RUN mkdir -p /app/test1
WORKDIR /app/test1
ADD testJenkins02-0.0.1-SNAPSHOT.jar /app/test1
EXPOSE 19016
ENTRYPOINT ["java","-jar","testJenkins02-0.0.1-SNAPSHOT.jar"]