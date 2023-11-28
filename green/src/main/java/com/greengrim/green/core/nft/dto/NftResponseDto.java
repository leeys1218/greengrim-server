package com.greengrim.green.core.nft.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class NftResponseDto {

    @Getter
    @AllArgsConstructor
    public static class NftId {
        private Long id;
    }
}
