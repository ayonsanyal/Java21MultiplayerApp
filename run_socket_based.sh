#!/bin/bash

echo "Running Socket Based Messaging (Players as separate process)"


# Build project
mvn clean compile

if [ $? -ne 0 ]; then
  echo "Build failed. Exiting."
  exit 1
fi

echo "Starting receiver player 2"
mvn exec:java@receiver &
RECEIVER_PID=$!

# Give receiver time to bind port
sleep 2

echo "Starting initiator player1"
mvn exec:java@initiator

# Wait for receiver to finish
wait $RECEIVER_PID


echo "Point 7 execution finished."