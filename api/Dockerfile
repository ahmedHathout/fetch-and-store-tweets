FROM openjdk:8-alpine

RUN apk update && apk add bash

RUN mkdir -p /app/api

COPY ./target/api-0.0.1-SNAPSHOT.jar /app/api/api-0.0.1-SNAPSHOT.jar

CMD java -Dspring.data.mongodb.uri=mongodb://mongodb:27017 -jar /app/api/api-0.0.1-SNAPSHOT.jar