package com.greengrim.green.core.chatRoom;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class ChatRoom implements Serializable {

  private static final long serialVersionUID = 6494678977089006639L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long roomId;
  private String title;
  private long userCount;

  public static ChatRoom create(String title) {
    ChatRoom chatRoom = new ChatRoom();
    chatRoom.title = title;
    return chatRoom;
  }

}
