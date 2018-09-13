#!/bin/bash
mvn clean install -DskipTests=true
docker-compose down
docker-compose up --build
