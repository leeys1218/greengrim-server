package com.greengrim.green.common.handler;

import com.greengrim.green.common.jwt.JwtTokenProvider;
import com.greengrim.green.core.chat.ChatService;
import com.greengrim.green.core.chat.dto.ChatMessage;
import com.greengrim.green.core.chat.dto.ChatMessage.MessageType;
import com.greengrim.green.core.chatRoom.ChatRoomRepository;
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
  private final ChatRoomRepository chatRoomRepository;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

    if(StompCommand.CONNECT == accessor.getCommand()) {
      String jwtToken = accessor.getFirstNativeHeader("token");
      log.info("CONNECT {}", jwtToken);
      jwtTokenProvider.validateAccessToken(jwtToken);
    }
    else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
      String roomId = chatService.getRoomId(Optional.ofNullable(
          (String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId"));
      String sessionId = (String) message.getHeaders().get("simpSessionId");

      chatRoomRepository.setUserEnterInfo(sessionId, roomId);
      chatRoomRepository.plusUserCount(roomId);

      String name = Optional.ofNullable((Principal) message.getHeaders()
              .get("simpUser")).map(Principal::getName).orElse("UnknownUser");
      chatService.sendChatMessage(ChatMessage.builder()
          .type(MessageType.ENTER).roomId(roomId).sender(name).build());
      log.info("SUBSCRIBED {}, {}", name, roomId);
    }
    else if (StompCommand.DISCONNECT == accessor.getCommand()) {
      String sessionId = (String) message.getHeaders().get("simpSessionId");
      String roomId = chatRoomRepository.getUserEnterRoomId(sessionId);

      chatRoomRepository.minusUserCount(roomId);

      String name = Optional.ofNullable((Principal) message.getHeaders()
          .get("simpUser")).map(Principal::getName).orElse("UnknownUser");
      chatService.sendChatMessage(ChatMessage.builder()
          .type(MessageType.QUIT).roomId(roomId).sender(name).build());

      chatRoomRepository.removeUserEnterInfo(sessionId);
      log.info("DISCONNECTED {}, {}", sessionId, roomId);
    }

    return message;
  }
}
