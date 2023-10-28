package com.greengrim.green.core.chat;

import com.greengrim.green.common.jwt.JwtTokenProvider;
import com.greengrim.green.core.chat.dto.ChatMessage;
import com.greengrim.green.core.chatRoom.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {
  private final JwtTokenProvider jwtTokenProvider;
  private final ChatRoomRepository chatRoomRepository;
  private final ChatService chatService;

  @MessageMapping("/chat/message")
  public void message(ChatMessage message, @Header("token") String token) {
    String nickname = jwtTokenProvider.getMemberIdByAccessToken(token);

    message.setSender(nickname);
    message.setUserCount(chatRoomRepository.getUserCount(message.getRoomId()));

    chatService.sendChatMessage(message);
  }
}
