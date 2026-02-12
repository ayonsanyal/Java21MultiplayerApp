#!/bin/bash


echo "Running Point 5 (Thread based messaging app)"


# Build project
mvn clean compile

if [ $? -ne 0 ]; then
  echo "Build failed. Exiting."
  exit 1
fi

# Run point
mvn exec:java@thread-based-messaging

echo "ThreadBasedMessagingApp execution finished."
