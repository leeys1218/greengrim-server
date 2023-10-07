package com.greengrim.green.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class BaseException extends RuntimeException {
    private final String errorCode;
    private String message;
    private final HttpStatus status;

    public BaseException(ErrorCode code) {
        errorCode = code.getErrorCode();
        message = code.getMessage();
        status = code.getStatus();
    }
}
