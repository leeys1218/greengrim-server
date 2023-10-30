package com.greengrim.green.core.chatRoom;

import jakarta.annotation.Resource;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChatRoomRedisRepository {

  private static final String CHAT_ROOMS = "CHAT_ROOM";
  private static final String ENTER_INFO = "ENTER_INFO";
  private final ChatRoomService chatRoomService;

  @Resource(name = "redisTemplate")
  private HashOperations<String, String, ChatRoom> hashOpsChatRoom;
  @Resource(name = "redisTemplate")
  private HashOperations<String, String, String> hashOpsEnterInfo;

  public List<ChatRoom> findAllRoom() {
   return hashOpsChatRoom.values(CHAT_ROOMS);
  }

  public ChatRoom findRoomById(Long id) {
    return hashOpsChatRoom.get(CHAT_ROOMS, id);
  }

  public ChatRoom createChatRoom(String title) {
    ChatRoom chatRoom = chatRoomService.createChatRoom(title);
    hashOpsChatRoom.put(CHAT_ROOMS, String.valueOf(chatRoom.getRoomId()), chatRoom);
    return chatRoom;
  }

  public void setUserEnterInfo(String sessionId, String roomId) {
    hashOpsEnterInfo.put(ENTER_INFO, sessionId, roomId);
  }

  public String getUserEnterRoomId(String sessionId) {
    return hashOpsEnterInfo.get(ENTER_INFO, sessionId);
  }

  public void removeUserEnterInfo(String sessionId) {
    hashOpsEnterInfo.delete(ENTER_INFO, sessionId);
  }

  public void removeChatRoom(String roomId) {
    hashOpsChatRoom.delete(CHAT_ROOMS, roomId);
  }
}
