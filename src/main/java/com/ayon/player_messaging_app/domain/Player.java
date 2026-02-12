package com.ayon.player_messaging_app.domain;

import com.ayon.player_messaging_app.service.MessagingService;

public class Player implements Runnable {

  private final String name;
  private final String id;
  private final MessagingService messagingService;
  private int maxMessages;
  private int sentCount = 0;
  private int receivedCount = 0;
  private boolean initiator;

  public Player(
      String name,
      String id,
      MessagingService messagingService,
      int maxMessages,
      boolean initiator) {
    this.name = name;
    this.id = id;
    this.messagingService = messagingService;
    this.maxMessages = maxMessages;
    this.initiator = initiator;
  }

  // Message processing logic
  @Override
  public void run() {
    try {
      while (receivedCount < maxMessages) {

        Message message = messagingService.receive();
        receivedCount++;

        if (sentCount < maxMessages) {
          sentCount++;

          Message reply =
              new Message(
                  message.toPlayer(),
                  message.fromPlayer(),
                  "sending-" + sentCount,
                  ServerCommand.Play,
                  sentCount);

          messagingService.send(reply);
        }
      }

      // Only initiator sends termination
      if (initiator) {
        System.out.println("Reached the limit now, now triggering the termination");
        messagingService.send(
            new Message(id, "other", "terminate", ServerCommand.TERMINATE, sentCount));
      }

    } catch (Exception ignored) {

    } finally {
      System.out.println(id + " - sentCount=" + sentCount + " receivedCount=" + receivedCount);
    }
  }

  public static Player createPlayer(
      String name,
      String id,
      MessagingService messagingService,
      int maxMessages,
      boolean initiator) {
    return new Player(name, id, messagingService, maxMessages, initiator);
  }
}
