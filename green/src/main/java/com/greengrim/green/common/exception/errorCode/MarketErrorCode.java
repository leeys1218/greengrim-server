package com.greengrim.green.common.exception.errorCode;

import com.greengrim.green.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MarketErrorCode implements ErrorCode {
    EMPTY_MARKET("MARKET_001", "존재하지 않는 마켓입니다.", HttpStatus.CONFLICT),
    INVALID_PRICE("MARKET_002", "가격이 적절하지 않습니다.", HttpStatus.BAD_REQUEST),
    NO_AUTHORIZATION("MARKET_003", "Market에 대해 권한이 없습니다", HttpStatus.BAD_REQUEST),
    UNABLE_PURCHASE("MARKET_004", "본인의 NFT는 구매할 수 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_TERM("MARKET_005", "유효한 기간이 아닙니다.", HttpStatus.BAD_REQUEST),
    ALREADY_IN_MARKET("MARKET_006","이미 판매중인 NFT 입니다.",HttpStatus.BAD_REQUEST),
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
