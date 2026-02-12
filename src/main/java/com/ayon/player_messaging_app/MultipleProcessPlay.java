package com.ayon.player_messaging_app;

import com.ayon.player_messaging_app.domain.Message;
import com.ayon.player_messaging_app.domain.Player;
import com.ayon.player_messaging_app.service.MessagingService;
import com.ayon.player_messaging_app.service.SocketBasedMessagingService;
import java.net.ServerSocket;
import java.net.Socket;

public class MultipleProcessPlay {

  public static void main(String[] args) throws Exception {

    if (args.length == 0) {
      System.out.println("Usage: java MultipleProcessPlay [initiator|receiver]");
      return;
    }

    boolean initiator = args[0].equalsIgnoreCase("initiator");
    Socket socket;

    if (initiator) {
      socket = new Socket("localhost", 9000);
      System.out.println("Connected as initiator");
    } else {
      ServerSocket serverSocket = new ServerSocket(9000);
      socket = serverSocket.accept();
      System.out.println("Receiver connected");
    }
    MessagingService messagingService = new SocketBasedMessagingService(socket);

    Player player =
        Player.createPlayer(
            initiator ? "1" : "2",
            initiator ? "initiator" : "player2",
            messagingService,
            10,
            initiator);

    Thread playerThread = Thread.startVirtualThread(player);

    if (initiator) {
      Message initiatorMessage = Message.initiatorMessage();
      // Initiate the messaging "send-1"
      messagingService.send(initiatorMessage);
    }

    playerThread.join();
    messagingService.close();

    Thread.sleep(5000);
    System.out.println("Finished execution socket based messaging.");
  }
}
