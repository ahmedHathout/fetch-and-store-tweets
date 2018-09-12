FROM openjdk:8-alpine

RUN apk update && apk add bash

RUN mkdir -p /app/storage

COPY ./target/storage-0.0.1-SNAPSHOT.jar /app/storage/storage-0.0.1-SNAPSHOT.jar

CMD java -Dspring.data.mongodb.uri=mongodb://mongodb:27017 -Dspring.kafka.producer.bootstrap-servers=kafka://kafka:9092 -jar /app/storage/storage-0.0.1-SNAPSHOT.jar
