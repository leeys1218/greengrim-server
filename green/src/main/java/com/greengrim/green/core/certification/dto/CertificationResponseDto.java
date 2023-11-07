package com.greengrim.green.core.certification.dto;

import static com.greengrim.green.common.entity.Time.calculateTime;

import com.greengrim.green.core.certification.Certification;
import com.greengrim.green.core.challenge.dto.ChallengeResponseDto.ChallengeInfoForCertification;
import com.greengrim.green.core.member.dto.MemberResponseDto.MemberSimpleInfo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CertificationResponseDto {

    @Getter
    @RequiredArgsConstructor
    public static class CertificationInfo {
        String title;
        String description;
        String imgUrl;
        String createdAt;

        public CertificationInfo(Certification certification) {
            this.title = "[" + certification.getRound() + "회차 인증]";
            this.description = certification.getDescription();
            this.imgUrl = certification.getImgUrl();
            this.createdAt = calculateTime(certification.getCreatedAt(), 3);
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class CertificationDetailInfo {
        ChallengeInfoForCertification challengeInfo;
        MemberSimpleInfo memberSimpleInfo;
        CertificationInfo certificationInfo;
        boolean isVerified;

        public CertificationDetailInfo(Certification certification, boolean isVerified) {
            this.memberSimpleInfo = new MemberSimpleInfo(certification.getMember());
            this.challengeInfo = new ChallengeInfoForCertification(certification.getChallenge());
            this.certificationInfo = new CertificationInfo(certification);
            this.isVerified = isVerified;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class CertificationsByMonth {
        List<String> date;
    }


}
