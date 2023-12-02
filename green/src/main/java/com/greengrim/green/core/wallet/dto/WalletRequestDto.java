package com.greengrim.green.core.wallet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class WalletRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckPassword {
        @NotBlank(message = "비밀번호는 빈 칸일 수 없습니다.")
        private String password;
    }

}
