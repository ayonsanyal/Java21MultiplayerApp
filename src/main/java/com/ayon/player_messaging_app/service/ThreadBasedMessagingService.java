package com.ayon.player_messaging_app.service;

import com.ayon.player_messaging_app.domain.Message;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadBasedMessagingService implements MessagingService {

  private BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<>();

  // Private constructor prevents external instantiation
  private ThreadBasedMessagingService(BlockingQueue<Message> messageQueue) {
    this.messageQueue = messageQueue;
  }

  /**
   * SingletonHolder class is loaded only when getInstance() is called. This guarantees: - Lazy
   * initialization - Thread safety (JVM class loading guarantees) - No synchronization overhead
   */
  private static class SingletonHolder {
    private static final BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<>();
    private static final ThreadBasedMessagingService INSTANCE =
        new ThreadBasedMessagingService(messageQueue);
  }

  public static ThreadBasedMessagingService getInstance() {
    return SingletonHolder.INSTANCE;
  }

  @Override
  // Called by "players" (actually simulated by main thread here)
  public void send(Message message) {
    messageQueue.offer(message);
  }

  @Override
  public Message receive() throws InterruptedException {
    return messageQueue.take();
  }

  @Override
  public void close() {}
}
