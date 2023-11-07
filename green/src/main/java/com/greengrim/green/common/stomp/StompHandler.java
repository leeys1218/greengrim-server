package com.greengrim.green.common.stomp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
  private final StompHandlerFunction stompHandlerFunction;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

    if(StompCommand.CONNECT == accessor.getCommand())
      stompHandlerFunction.connect(accessor, message);
    else if (StompCommand.SUBSCRIBE == accessor.getCommand())
      stompHandlerFunction.subscribe(message);
    else if (StompCommand.UNSUBSCRIBE == accessor.getCommand())
      stompHandlerFunction.unsubscribe(message);
    else if (StompCommand.DISCONNECT == accessor.getCommand())
      stompHandlerFunction.disconnect(accessor, message);

    return message;
  }
}
