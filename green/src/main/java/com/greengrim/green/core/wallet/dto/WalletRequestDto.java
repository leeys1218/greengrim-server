package com.greengrim.green.core.wallet.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class WalletRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckPassword {
        @NotBlank(message = "비밀번호는 빈칸일 수 없습니다.")
        private String password;
    }
}
