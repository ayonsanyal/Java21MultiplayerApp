package com.ayon.player_messaging_app.service;

import com.ayon.player_messaging_app.domain.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketBasedMessagingService implements MessagingService {

  private final ObjectInputStream in;
  private final ObjectOutputStream out;
  private final Socket socket;

  public SocketBasedMessagingService(Socket socket) throws IOException {
    this.out = new ObjectOutputStream(socket.getOutputStream());
    out.flush();
    this.in = new ObjectInputStream(socket.getInputStream());
    this.socket = socket;
  }

  @Override
  public void send(Message message) {
    try {
      out.writeObject(message);
      out.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Message receive() throws InterruptedException {
    try {
      return (Message) in.readObject();
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void close() {
    try {
      socket.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
