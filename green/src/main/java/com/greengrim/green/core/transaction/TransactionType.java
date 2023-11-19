package com.greengrim.green.core.transaction;

public enum TransactionType {
    MINTING("NFT 발행"),
    SWAP1("클레이를 그린으로 스왑"),
    SWAP2("그린을 클레이로 스왑"),
    SEND_OUT_KLAY("클레이 외부 전송"),
    DEAL("거래");

    TransactionType(String name) {
    }
}
