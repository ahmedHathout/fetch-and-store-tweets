#!/bin/bash
mvn clean install
docker-compose down
docker-compose up --build
