package com.greengrim.green.core.nft.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NftRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterNft {
        @NotBlank(message = "비밀번호가 일치하지 않습니다.")
        private String password;
        @NotBlank(message = "제목은 빈값일 수 없습니다.")
        private String title;
        @NotBlank(message = "설명은 빈값일 수 없습니다.")
        private String description;
        @NotBlank(message = "에셋은 빈값일 수 없습니다.")
        private String asset;
    }
}
