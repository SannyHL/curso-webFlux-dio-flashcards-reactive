FROM maven:3.8.5-openjdk-17 AS builder



WORKDIR /app
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn clean package

FROM openjdk:17-jdk

ENV INSTALL_PATH /flashcards-reactive

RUN mkdir -p $INSTALL_PATH

WORKDIR $INSTALL_PATH

COPY --from=builder /app/target/flashcards-reactive-1.0.0.jar .

COPY ./start.sh .

RUN chmod +x start.sh

CMD ["./start.sh"]