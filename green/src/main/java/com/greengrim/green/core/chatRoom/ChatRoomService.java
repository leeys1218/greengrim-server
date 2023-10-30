package com.greengrim.green.core.chatRoom;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@RequiredArgsConstructor
@Service
public class ChatRoomService {

  private final ChatRoomRepository chatRoomRepository;

  public void save(ChatRoom chatRoom) {
    chatRoomRepository.save(chatRoom);
  }

  public ChatRoom createChatRoom(String title) {
    ChatRoom chatRoom = ChatRoom.builder()
        .title(title)
        .userCount(0L)
        .build();
    save(chatRoom);
    return chatRoom;
  }

}
