package com.greengrim.green.core.challenge;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {

    ECO_PRODUCT("에코 제품 사용", 1.5),
    COOL_AND_HOT_LOOKING("쿨맵시 & 온맵시", 1.5),
    PICK_UP_KING("줍킹", 1.5),
    GROWING_PLANT("식물 키우기", 1.5),
    DAILY("일상", 1.5),
    ELECTRIC_CAR("전기차", 1.5),
    PUBLIC_TRANSPORTATION("대중 교통 이용", 1.5),
    MAINTAINING_TEMPERATURE("적정 온도 유지", 1.5),
    RECYCLING("분리수거 라벨 제거", 1.5);

    private final String name;
    private final double carbonReduction;
}
