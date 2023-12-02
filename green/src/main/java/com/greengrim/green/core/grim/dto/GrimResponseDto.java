package com.greengrim.green.core.grim.dto;

import com.greengrim.green.core.grim.Grim;
import com.greengrim.green.core.member.dto.MemberResponseDto.MemberSimpleInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class GrimResponseDto {

    @Getter
    @AllArgsConstructor
    public static class GrimInfo {
        private final Long id;
        private final String imgUrl;
        private final String title;
        private final MemberSimpleInfo memberSimpleInfo;

        public GrimInfo(Grim grim) {
            this.id = grim.getId();
            this.imgUrl = grim.getImgUrl();
            this.title = grim.getTitle();
            this.memberSimpleInfo = new MemberSimpleInfo(grim.getMember());
        }
    }
}
