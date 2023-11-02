package com.greengrim.green.common.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageResponseDto<T> {

    private int page;         // 몇 페이지인가
    private boolean hasNext;  // 다음 페이지가 있는가
    private T result;         // 보여줄 List
}