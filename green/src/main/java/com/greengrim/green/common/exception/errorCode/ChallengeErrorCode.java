package com.greengrim.green.common.exception.errorCode;

import com.greengrim.green.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ChallengeErrorCode implements ErrorCode {
    EMPTY_CHALLENGE("CHALLENGE_001", "존재하지 않는 챌린지입니다.", HttpStatus.CONFLICT)
    ;

    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
