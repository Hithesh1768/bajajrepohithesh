#!/bin/bash

echo "Starting Bajaj Health Qualifier..."
echo

echo "Building project..."
mvn clean install

echo
echo "Running application..."
mvn spring-boot:run
