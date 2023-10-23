package com.greengrim.green.core.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greengrim.green.core.chatRoom.ChatRoom;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

  private final ObjectMapper objectMapper;
  private HashMap<String, ChatRoom> chatRooms;

  @PostConstruct
  private void init() {
    chatRooms = new LinkedHashMap<>();
  }

  public List<ChatRoom> findAllRoom() {
    return new ArrayList<>(chatRooms.values());
  }

  public ChatRoom findRoomById(String roomId) {
    return chatRooms.get(roomId);
  }

  public ChatRoom createRoom(String name) {
    String randomId = UUID.randomUUID().toString();
    ChatRoom chatRoom = ChatRoom.builder()
        .roomId(randomId)
        .name(name)
        .build();
    chatRooms.put(randomId, chatRoom);
    return chatRoom;
  }

  public <T> void sendMessage(WebSocketSession session, T message) {
    try {
      session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }

}
