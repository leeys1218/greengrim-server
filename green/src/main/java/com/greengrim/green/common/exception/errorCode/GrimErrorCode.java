package com.greengrim.green.common.exception.errorCode;

import com.greengrim.green.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GrimErrorCode implements ErrorCode {
    EMPTY_GRIM("GRIM_001", "유효하지 않은 그림입니다.", HttpStatus.CONFLICT),
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
