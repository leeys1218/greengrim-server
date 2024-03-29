package com.greengrim.green.common.exception.errorCode;

import com.greengrim.green.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum WalletErrorCode implements ErrorCode {
    EMPTY_WALLET("WALLET_001", "지갑이 없습니다.", HttpStatus.FORBIDDEN),
    WRONG_PWD("WALLET_002", "비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    FAILED_CREATE_WALLET("WALLET_003", "지갑 생성에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    FAILED_USE_WALLET("WALLET_004", "3분간은 지갑을 이용할 수 없습니다.", HttpStatus.CONFLICT),
    FIVE_WRONG_PASSWORD("WALLET_006", "비밀번호를 5번 이상 잘못 입력하였습니다.", HttpStatus.FORBIDDEN),
    FAILED_GET_KLAY("WALLET_007", "KLAY 확인에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_ENOUGH_COIN("WALLET_008", "보유한 코인이 충분치 않습니다.", HttpStatus.CONFLICT),
    FAILED_SEND_KLAY("WALLET_009", "KLAY 전송에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
