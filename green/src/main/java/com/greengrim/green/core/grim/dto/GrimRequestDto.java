package com.greengrim.green.core.grim.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class GrimRequestDto {

    @Getter
    @AllArgsConstructor
    public static class UpdateTitle {
        private Long id;
        private String title;
    }
}
