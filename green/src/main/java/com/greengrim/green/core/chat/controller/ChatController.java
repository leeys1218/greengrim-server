package com.greengrim.green.core.chat.controller;

import com.greengrim.green.common.jwt.JwtTokenProvider;
import com.greengrim.green.core.chat.ChatMessage;
import com.greengrim.green.core.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {
  private final JwtTokenProvider jwtTokenProvider;
  private final ChatService chatService;

  /**
   * [MessageMapping] 메세지 전송
   */
  @MessageMapping("/chat/message")
  public void message(ChatMessage message, @Header("token") String token) {
    String senderId = jwtTokenProvider.getMemberIdByAccessToken(token);

    message.setSenderId(Long.valueOf(senderId));
    chatService.sendChatMessage(message);
  }
}
