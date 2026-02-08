package com.ayon.player_messaging_app;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import com.ayon.player_messaging_app.domain.Message;
import com.ayon.player_messaging_app.domain.Player;
import com.ayon.player_messaging_app.service.ServerCommand;
import com.ayon.player_messaging_app.service.PingPongMessagingService;

public class SingleProcessPlay {

    public static void main(String[] args) throws Exception {
        Player player1 = new Player("1", "initiator");
        Player player2 = new Player("2", "player2");

        PingPongMessagingService pingPongMessagingService = new PingPongMessagingService(20);
        pingPongMessagingService.start();

        // Kick off the very first "send-1"
        Thread.startVirtualThread(() -> {
            pingPongMessagingService.send(
                    new Message(player1, player2, "send-1", ServerCommand.Play, 1)
                                         );
        });

        Thread.sleep(5000);
        System.out.println("Finished execution.");
    }
}
