package com.greengrim.green.core.chat;

import com.greengrim.green.core.chat.ChatMessage.MessageType;
import com.greengrim.green.core.chatRoom.ChatRoomRepository;
import com.greengrim.green.core.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatService {

  private final ChannelTopic channelTopic;
  private final RedisTemplate redisTemplate;
  private final MemberRepository memberRepository;

  public String getRoomId(String destination) {
    int lastIndex = destination.lastIndexOf('/');
    if (lastIndex != -1)
      return destination.substring(lastIndex + 1);
    else
      return "";
  }

  public void sendChatMessage(ChatMessage chatMessage) {

    if(MessageType.ENTER.equals(chatMessage.getType())) {

      String senderNickName = memberRepository.findByIdAndStatusTrue(chatMessage.getSenderId())
          .get().getNickName();

      chatMessage.setMessage(senderNickName + "님이 방에 입장했습니다.");
    }
    else if (MessageType.QUIT.equals(chatMessage.getType())) {

      String senderNickName = memberRepository.findByIdAndStatusTrue(chatMessage.getSenderId())
          .get().getNickName();

      chatMessage.setMessage(senderNickName + "님이 방에서 나갔습니다.");
    }

    redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
  }
}
