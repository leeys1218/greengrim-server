package com.greengrim.green.core.verification.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class VerificationRequest {

    @Getter
    @AllArgsConstructor
    public static class RegisterVerification {
        @NotNull
        private Long certificationId;
        @NotNull
        private boolean response;
    }
}
