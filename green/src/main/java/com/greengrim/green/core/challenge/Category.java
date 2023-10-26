package com.greengrim.green.core.challenge;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {

    ECO_PRODUCT("에코 제품 사용"),
    COOL_AND_HOT_LOOKING("쿨맵시 & 온맵시"),
    PICK_UP_KING("줍킹"),
    GROWING_PLANT("식물 키우기"),
    DAILY("일상"),
    ELECTRIC_CAR("전기차"),
    PUBLIC_TRANSPORTATION("대중 교통 이용"),
    MAINTAINING_TEMPERATURE("적정 온도 유지"),
    RECYCLING("분리수거 라벨 제거");

    private final String name;
}
