package com.greengrim.green.core.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
  public ChatMessage() {
  }

  @Builder
  public ChatMessage(MessageType type, Long roomId, Long senderId, String message) {
    this.type = type;
    this.roomId = roomId;
    this.senderId = senderId;
    this.message = message;
  }

  public enum MessageType {
      ENTER, QUIT, TALK;
  }
  private MessageType type;
  private Long roomId;
  private Long senderId;
  private String message;

}
