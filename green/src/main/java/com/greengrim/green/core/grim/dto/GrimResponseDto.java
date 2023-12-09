package com.greengrim.green.core.grim.dto;

import static com.greengrim.green.common.entity.Time.calculateTime;

import com.greengrim.green.core.grim.Grim;
import com.greengrim.green.core.member.dto.MemberResponseDto.MemberSimpleInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class GrimResponseDto {

    @Getter
    @AllArgsConstructor
    public static class GrimSimpleInfo {
        private final Long id;
        private final String imgUrl;
        private final String title;

        public GrimSimpleInfo(Grim grim) {
            this.id = grim.getId();
            this.imgUrl = grim.getImgUrl();
            this.title = grim.getTitle();
        }
    }

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

    @Getter
    @AllArgsConstructor
    public static class GrimDetailInfo {
        private final GrimInfo grimInfo;
        private final String createdAt;
        private final boolean isMine;

        public GrimDetailInfo(Grim grim, boolean isMine) {
            this.grimInfo = new GrimInfo(grim);
            this.createdAt = calculateTime(grim.getCreatedAt(), 1);
            this.isMine = isMine;
        }
    }
}
