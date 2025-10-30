#FROM ubuntu:latest
#LABEL authors="17706"
#
#ENTRYPOINT ["top", "-b"]

FROM openjdk:8-jdk

RUN mkdir -p /jenkinsTest/test1

WORKDIR /jenkinsTest/test1

ADD ./target/testJenkins02-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 19016

ENTRYPOINT ["java","-jar","app.jar"]