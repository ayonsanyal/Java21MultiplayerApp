#!/bin/bash

# Fail fast
set -e

# Clean and package
mvn clean package -DskipTests

# Run Main class with Maven exec plugin
mvn exec:java -Dexec.mainClass="Main"
