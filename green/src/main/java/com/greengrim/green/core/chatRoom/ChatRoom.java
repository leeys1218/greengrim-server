package com.greengrim.green.core.chatRoom;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@Entity
@AllArgsConstructor
public class ChatRoom implements Serializable {

  private static final long serialVersionUID = 6494678977089006639L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long roomId;
  private String title;
  private Long userCount;

  public ChatRoom() {
  }
}
