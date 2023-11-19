package com.greengrim.green.core.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TransactionRequest {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionSetDto {
        private String payTransaction;
        private String payBackTransaction;
        private String feeTransaction;
    }

}
