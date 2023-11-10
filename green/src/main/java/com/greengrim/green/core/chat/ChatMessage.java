package com.greengrim.green.core.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
  public
  ChatMessage() {
  }

  @Builder
  public ChatMessage(MessageType type, Long roomId, Long senderId,
      Long certId, String message, String nickName, String profileImg, String certImg) {
    this.type = type;
    this.roomId = roomId;
    this.senderId = senderId;
    this.certId = certId;
    this.message = message;
    this.nickName = nickName;
    this.profileImg = profileImg;
    this.certImg = certImg;
  }

  public enum MessageType {
      ENTER, QUIT, TALK, CERT
  }
  private MessageType type;
  private Long roomId;
  private Long senderId;
  private Long certId;
  private String message;
  private String nickName;
  private String profileImg;
  private String certImg;

}
