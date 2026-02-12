package com.ayon.player_messaging_app.service;

import com.ayon.player_messaging_app.domain.Message;

public interface MessagingService {

  void send(Message message);

  Message receive() throws InterruptedException;

  void close();
}
