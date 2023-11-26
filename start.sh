#!/bin/sh



mvn clean package

#java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar target/flashcards-reactive-1.0.0.jar
java -jar target/flashcards-reactive-1.0.0.jar