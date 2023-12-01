package com.greengrim.green.common.exception.errorCode;

import com.greengrim.green.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum VerificationErrorCode implements ErrorCode {
    EXIST_VERIFICATION("VERIFICATION_001", "이미 참여한 검증입니다.", HttpStatus.CONFLICT),
    NOT_EXIST_VERIFICATION("VERIFICATION_002", "참여할 수 있는 검증이 없습니다.", HttpStatus.CONFLICT),
    SELF_VERIFICATION("VERIFICATION_003", "자기 자신의 인증은 검증할 수 없습니다.", HttpStatus.CONFLICT)
    ;

    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
