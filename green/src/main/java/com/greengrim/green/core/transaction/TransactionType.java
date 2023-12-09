package com.greengrim.green.core.transaction;

public enum TransactionType {
    MINTING("NFT 발행"),
    SEND_OUT_KLAY("클레이 외부 전송"),
    DEAL("거래");

    TransactionType(String name) {
    }
}
