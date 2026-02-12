package com.ayon.player_messaging_app;

import com.ayon.player_messaging_app.domain.Message;
import com.ayon.player_messaging_app.domain.Player;
import com.ayon.player_messaging_app.service.MessagingService;
import com.ayon.player_messaging_app.service.ThreadBasedMessagingService;

public class SingleProcessPlay {

  public static void main(String[] args) throws Exception {

    MessagingService messagingService = ThreadBasedMessagingService.getInstance();
    Player player1 = Player.createPlayer("1", "initiator", messagingService, 10, false);
    Player player2 = Player.createPlayer("2", "player2", messagingService, 10, false);

    Thread.startVirtualThread(player1);
    Thread.startVirtualThread(player2);

    Message initiatorMessage = Message.initiatorMessage();
    // Initiate the messaging "send-1"
    messagingService.send(initiatorMessage);

    Thread.sleep(5000);
    System.out.println("Finished execution thread based messaging.");
  }
}
