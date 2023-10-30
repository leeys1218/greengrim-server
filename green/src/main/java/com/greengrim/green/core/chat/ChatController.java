package com.greengrim.green.core.chat;

import com.greengrim.green.common.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class ChatController {
  private final JwtTokenProvider jwtTokenProvider;
  private final ChatService chatService;

  @MessageMapping("/chat/message")
  public void message(ChatMessage message, @Header("token") String token) {
    String senderId = jwtTokenProvider.getMemberIdByAccessToken(token);

    // getMemberId의 return을 Long으로
    message.setSenderId(Long.valueOf(senderId));
    chatService.sendChatMessage(message);
  }

  @RequestMapping("visitor/chat/message/enter")
  public void enterMessage(ChatMessage message) {

  }

  @RequestMapping("visitor/chat/message/quit")
  public void QuitMessage(ChatMessage message) {

  }
}
