package com.greengrim.green.core.chat;

import com.greengrim.green.common.jwt.JwtTokenProvider;
import com.greengrim.green.core.chat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

  private final RedisTemplate redisTemplate;
  private final ChannelTopic channelTopic;
  private final JwtTokenProvider jwtTokenProvider;

  @MessageMapping("/chat/message")
  public void message(ChatMessage message, @Header("token") String token) {
    String nickname = jwtTokenProvider.getMemberIdByAccessToken(token);
    message.setSender(nickname);

    if(ChatMessage.MessageType.ENTER.equals(message.getType())) {
      message.setSender("[알림]");
      message.setMessage(nickname + "님이 입장하셨습니다.");
    }
    redisTemplate.convertAndSend(channelTopic.getTopic(), message);
  }
}
