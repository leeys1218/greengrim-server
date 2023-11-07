package com.greengrim.green.core.chat.service;

import com.greengrim.green.core.chat.ChatMessage;
import com.greengrim.green.core.chat.ChatMessage.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

  private final ChannelTopic channelTopic;
  private final RedisTemplate redisTemplate;

  public String getRoomId(String destination) {
    int lastIndex = destination.lastIndexOf('/');
    if (lastIndex != -1)
      return destination.substring(lastIndex + 1);
    else
      return "";
  }

  public void sendChatMessage(ChatMessage chatMessage) {

    if(MessageType.ENTER.equals(chatMessage.getType())) {

    }
    else if (MessageType.QUIT.equals(chatMessage.getType())) {

    }

    redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
  }
}
