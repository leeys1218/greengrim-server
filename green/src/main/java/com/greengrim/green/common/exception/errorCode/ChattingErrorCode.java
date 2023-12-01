package com.greengrim.green.common.exception.errorCode;

import com.greengrim.green.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ChattingErrorCode implements ErrorCode {

  FAIL_FCM_SUBSCRIBE("CHATTING_001", "FCM 토픽 구독에 실패했습니다.", HttpStatus.CONFLICT),
  FAIL_FCM_UNSUBSCRIBE("CHATTING_002", "FCM 토픽 구독취소에 실패했습니다.", HttpStatus.CONFLICT),
  FAIL_SEND_MESSAGE("CHATTING_003", "메세지 전송에 실패했습니다", HttpStatus.CONFLICT),
  ;

  private final String errorCode;
  private final String message;
  private final HttpStatus status;

}
