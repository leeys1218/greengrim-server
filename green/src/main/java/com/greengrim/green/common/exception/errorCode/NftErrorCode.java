package com.greengrim.green.common.exception.errorCode;

import com.greengrim.green.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum NftErrorCode implements ErrorCode {
    EMPTY_NFT("NFT_001", "유효하지 않은 NFT입니다.", HttpStatus.CONFLICT),
    FAILED_CREATE_ASSET("NFT_002", "에셋 생성에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    FAILED_CREATE_METADATA("NFT_003", "메타데이터 생성에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    FAILED_CREATE_NFT("NFT_004", "NFT 생성에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    FAILED_SEND_NFT("NFT_005", "NFT 전송에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
