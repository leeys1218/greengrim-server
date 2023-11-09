package com.greengrim.green.core.chat.service;

import com.greengrim.green.core.chat.ChatMessage;
import com.greengrim.green.core.chat.ChatMessage.MessageType;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.member.usecase.GetMemberUseCase;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

  private final ChannelTopic channelTopic;
  private final RedisTemplate redisTemplate;
  private final GetMemberUseCase getMemberUseCase;

  public String getRoomId(String destination) {
    int lastIndex = destination.lastIndexOf('/');
    if (lastIndex != -1)
      return destination.substring(lastIndex + 1);
    else
      return "";
  }

  public void sendChatMessage(ChatMessage chatMessage) {

    // CERT 타입이 아닐 떄
    if(!MessageType.CERT.equals(chatMessage.getType())) {
      chatMessage.setCertId(null);
      chatMessage.setCertImg("");
    }
    // ENTER, QUIT 타입일 때
    if(MessageType.ENTER.equals(chatMessage.getType()) ||
        MessageType.QUIT.equals(chatMessage.getType())) {
      chatMessage.setNickName(null);
      chatMessage.setProfileImg("");
    }
    // ENTER, QUIT 타입이 아닐 때
    else {
      Optional<Member> member = getMemberUseCase.findMemberById(chatMessage.getSenderId());
      chatMessage.setNickName(member.get().getNickName());
      chatMessage.setProfileImg(member.get().getProfileImgUrl());
    }

    redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
  }
}
