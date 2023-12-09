package com.greengrim.green.core.market.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MarketRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterNftToMarket {
        @NotBlank(message = "비밀번호가 일치하지 않습니다.")
        private String payPassword;
        @Positive(message = "잘못된 NFT Id 입니다.")
        private Long nftId;
        @Positive(message = "잘못된 가격입니다.")
        private double price;
    }
}
