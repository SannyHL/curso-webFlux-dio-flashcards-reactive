FROM maven:3.8.5-openjdk-17 AS builder

COPY ./settings.xml /root/.m2/settings.xml

WORKDIR /app
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn clean package

FROM openjdk:17-jdk

ENV INSTALL_PATH /flashcards-reactive

RUN mkdir -p $INSTALL_PATH

WORKDIR $INSTALL_PATH

COPY . .