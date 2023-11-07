package com.greengrim.green.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortOption {

    DESC("최신순"),
    ASC("오래된 순"),
    GREATEST("인원 많은 순"),
    LEAST("인원 적은 순");

    private final String description;
}
