package com.greengrim.green.common.handler;

import com.greengrim.green.common.jwt.JwtTokenProvider;
import com.greengrim.green.core.chat.ChatService;
import com.greengrim.green.core.chat.ChatMessage;
import com.greengrim.green.core.chat.ChatMessage.MessageType;
import com.greengrim.green.core.chatRoom.ChatRoomRedisRepository;
import java.security.Principal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

  private final JwtTokenProvider jwtTokenProvider;
  private final ChatService chatService;
  private final ChatRoomRedisRepository chatRoomRedisRepository;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

    if(StompCommand.CONNECT == accessor.getCommand()) {
      String jwtToken = accessor.getFirstNativeHeader("token");
      log.info("CONNECT {}", jwtToken);
      jwtTokenProvider.validateAccessToken(jwtToken);
    }
    else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {

      String roomId = chatService.getRoomId(Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("0"));
      String sessionId = (String) message.getHeaders().get("simpSessionId");

      // 챌린지 완성 후 수정 - 챌린지 service에서 getCapacity 보다 크면 던지기
      // if (chatRoomRepository.getUserCount(Long.valueOf(roomId)
      //        >  challengeService.getCapacity())

      // challengeService에서 현재 인원 수  늘리기
      chatRoomRedisRepository.setUserEnterInfo(sessionId, roomId);

      String Name = Optional.ofNullable((Principal) message.getHeaders()
              .get("simpUser")).map(Principal::getName).orElse("UnknownUser");

      chatService.sendChatMessage(ChatMessage.builder()
          .type(MessageType.ENTER)
          .roomId(Long.valueOf(roomId))
          .build());
      log.info("SUBSCRIBED {}, {}", Name, roomId);
    }
    else if (StompCommand.DISCONNECT == accessor.getCommand()) {
      String sessionId = (String) message.getHeaders().get("simpSessionId");
      String roomId = chatRoomRedisRepository.getUserEnterRoomId(sessionId);

      // Challenge Service에서 유저 수 줄이기 and 현재 인원 수가 0이라면 redis에서 해당 채널 삭제
      // challengeService.minusUser()
      // if(challengeService.current() == 0)
      //  chatRoomRepository.removeChatRoom(roomId);

      String name = Optional.ofNullable((Principal) message.getHeaders()
          .get("simpUser")).map(Principal::getName).orElse("UnknownUser");
      chatService.sendChatMessage(ChatMessage.builder()
          .type(MessageType.QUIT)
          .roomId(Long.valueOf(roomId))
          .build());

      chatRoomRedisRepository.removeUserEnterInfo(sessionId);
      log.info("DISCONNECTED {}, {}", sessionId, roomId);
    }

    return message;
  }
}
