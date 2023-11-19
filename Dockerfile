FROM openjdk:17-jdk-alpine

RUN apt-get update && apt-get install -qq - y --no-recommnds

ENV INSTALL_PATH / api.flashcards-reactive

RUN mkdir $INSTALL_PATH

WORKDIR $INSTALL_PATH

COPY . .