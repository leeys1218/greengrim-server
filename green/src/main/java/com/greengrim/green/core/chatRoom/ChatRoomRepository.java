package com.greengrim.green.core.chatRoom;

import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {

  private static final String CHAT_ROOMS = "CHAT_ROOM";
  private final RedisTemplate<String, Object> redisTemplate;
  private HashOperations<String, String, ChatRoom> opsHashChatRoom;

  @PostConstruct
  private void init() {
    opsHashChatRoom = redisTemplate.opsForHash();
  }

  public List<ChatRoom> findAllRoom() {
   return opsHashChatRoom.values(CHAT_ROOMS);
  }

  public ChatRoom findRoomById(String id) {
    return opsHashChatRoom.get(CHAT_ROOMS, id);
  }

  public ChatRoom createChatRoom(String name) {
    ChatRoom chatRoom = ChatRoom.create(name);
    opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);
    return chatRoom;
  }

}
