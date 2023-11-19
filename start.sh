#!/bin/sh

mvn clean compile

#java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar build/lib/api.flashcards-reactive-1.0.0.jar
java -jar target/flashcards-reactive-1.0.0.jar