package com.greengrim.green.common.exception.errorCode;

import com.greengrim.green.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum WalletErrorCode implements ErrorCode {
    EMPTY_WALLET("WALLET_001", "지갑이 없습니다.", HttpStatus.FORBIDDEN),
    FAILED_CREATE_WALLET("WALLET_002", "지갑 생성에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    FIVE_WRONG_PASSWORD("WALLET_003", "비밀번호를 5번 잘못 입력하였습니다.", HttpStatus.FORBIDDEN);

    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
