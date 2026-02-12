package com.ayon.player_messaging_app.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Domain record representing a message exchanged between players.
 *
 * @param fromPlayer sender's name
 * @param toPlayer receiver's name
 * @param messagePyload message content
 * @param counter sequence number of the message
 */
public record Message(
    String fromPlayer, String toPlayer, String messagePyload, ServerCommand command, int counter)
    implements Serializable {
  private static final long serialVersionUID = 1L;

  @Override
  public boolean equals(Object o) {

    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Message message = (Message) o;
    return counter == message.counter
        && Objects.equals(toPlayer, message.toPlayer)
        && Objects.equals(fromPlayer, message.fromPlayer)
        && Objects.equals(messagePyload, message.messagePyload)
        && command == message.command;
  }

  @Override
  public int hashCode() {

    return Objects.hash(fromPlayer, toPlayer, messagePyload, command, counter);
  }

  @Override
  public String toString() {

    return String.format(
        "{"
            + "\"fromPlayer\":\"%s\","
            + "\"toPlayer\":\"%s\","
            + "\"messagePyload\":%s},"
            + "\"command\":%s"
            + "\"counter\":%d"
            + "},",
        formatJson(fromPlayer),
        formatJson(toPlayer),
        formatJson(messagePyload),
        formatJson(command.name()),
        counter);
  }

  private String formatJson(String str) {

    if (str == null) {
      return "null";
    }
    return str.replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t");
  }

  public static Message initiatorMessage() {
    return new Message("player1", "player2", "send-1", ServerCommand.Play, 1);
  }
}
