package com.greengrim.green.core.chat;

import com.greengrim.green.common.redis.RedisPublisher;
import com.greengrim.green.core.chat.dto.ChatMessage;
import com.greengrim.green.core.chatRoom.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

  private final RedisPublisher redisPublisher;
  private final ChatRoomRepository chatRoomRepository;

  @MessageMapping("/chat/message")
  public void message(ChatMessage message) {
    if(ChatMessage.MessageType.ENTER.equals(message.getType())) {
      chatRoomRepository.enterChatRoom(message.getRoomId());
      message.setMessage(message.getSender() + "님이 입장하셨습니다.");
    }
    redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
  }
}
