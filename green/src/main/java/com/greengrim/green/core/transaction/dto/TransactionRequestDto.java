package com.greengrim.green.core.transaction.dto;

import com.greengrim.green.core.nft.Nft;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TransactionRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionSetDto {
        private String payTransaction;
        private String payBackTransaction;
        private String feeTransaction;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MintingTransactionDto {
        private Long buyerId;
        private Nft nft;
        private TransactionSetDto transactionSetDto;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PurchaseNftOnMarketTransactionDto {
        private Long buyerId;
        private Long sellerId;
        private double coin;        // 수수료를 제외한 양
        private Nft nft;
        private TransactionSetDto transactionSetDto;
    }

}
