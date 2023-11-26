package com.greengrim.green.core.chat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

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
  private String sentDate;
  private String sentTime;

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

  public void setSentTime() {
    LocalDateTime now = LocalDateTime.now();

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 E요일", Locale.KOREAN);
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("a h시 m분", Locale.KOREAN);

    this.sentDate = now.format(dateFormatter);
    this.sentTime = now.format(timeFormatter);
  }
}
