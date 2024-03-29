package com.greengrim.green.core.chat.controller;

import com.greengrim.green.core.chat.ChatMessage;
import com.greengrim.green.core.chat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
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
  @Operation(summary = "메세지 전송")
  @MessageMapping("/chat/message")
  public void message(ChatMessage message) {
    chatService.sendChatMessage(message);
  }
}
