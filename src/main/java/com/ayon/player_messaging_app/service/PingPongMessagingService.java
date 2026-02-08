package com.ayon.player_messaging_app.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import com.ayon.player_messaging_app.domain.Message;

public class PingPongMessagingService {

    private final BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    private final int maxMessages;

    public PingPongMessagingService(int maxMessages) {

        this.maxMessages = maxMessages;
    }

    // Start processing loop (on a virtual thread)
    public void start() {
        Thread.startVirtualThread(() -> {
            try {
                while (true) {
                    Message msg = queue.take();
                    processMessage(msg);

                    if (msg.command() == ServerCommand.TERMINATE) {
                        System.out.println("Terminate command received. Stopping game.");
                        break;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    // Called by "players" (actually simulated by main thread here)
    public void send(Message message) {

        queue.offer(message);
    }

    //Message Processing
    private void processMessage(Message message) {
        System.out.printf("[%s -> %s] %s%n",
                          message.fromPlayer().name(),
                          message.toPlayer().name(),
                          message.messagePyload());

        int next = message.counter() + 1;

        if (next <= maxMessages) {
            String newPayload = "sending-" + next;

            Message reply = new Message(
                    message.toPlayer(),
                    message.fromPlayer(),
                    newPayload,
                    ServerCommand.Play,
                    next
            );
            queue.offer(reply);
        } else {
            Message terminate = new Message(
                    message.toPlayer(),
                    message.fromPlayer(),
                    "terminating",
                    ServerCommand.TERMINATE,
                    next
            );
            queue.offer(terminate);
        }
    }
}
