package com.greengrim.green.core.chat.controller;

import com.greengrim.green.core.chat.ChatMessage;
import com.greengrim.green.core.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

  private final ChatService chatService;

  /**
   * [MessageMapping] 메세지 전송
   * Todo: @CurrentMember 헤더를 사용할 수 있다면 추가
   */
  @MessageMapping("/chat/message")
  public void message(ChatMessage message, @Header("token") String token) {
    chatService.sendChatMessage(message);
  }
}
