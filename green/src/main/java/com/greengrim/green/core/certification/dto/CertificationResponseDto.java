package com.greengrim.green.core.certification.dto;

import static com.greengrim.green.common.entity.Time.calculateTime;

import com.greengrim.green.core.certification.Certification;
import com.greengrim.green.core.certification.VerificationFlag;
import com.greengrim.green.core.challenge.dto.ChallengeResponseDto.ChallengeInfoForCertification;
import com.greengrim.green.core.challenge.dto.ChallengeResponseDto.ChallengeTitleInfo;
import com.greengrim.green.core.member.dto.MemberResponseDto.MemberSimpleInfo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CertificationResponseDto {

    @Getter
    @RequiredArgsConstructor
    public static class CertificationInfo {
        private Long id;
        private String title;
        private String description;
        private String imgUrl;
        private String createdAt;

        public CertificationInfo(Certification certification) {
            this.id = certification.getId();
            this.title = "[" + certification.getRound() + "회차 인증]";
            this.description = certification.getDescription();
            this.imgUrl = certification.getImgUrl();
            this.createdAt = calculateTime(certification.getCreatedAt(), 3);
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class registerCertificationResponse {
        private Long certId;
        private String certImg;

        public registerCertificationResponse(Certification certification) {
            this.certId = certification.getId();
            this.certImg = certification.getImgUrl();
        }
    }


    @Getter
    @RequiredArgsConstructor
    public static class CertificationDetailInfo {
        ChallengeInfoForCertification challengeInfo;
        MemberSimpleInfo memberSimpleInfo;
        CertificationInfo certificationInfo;
        private VerificationFlag isVerified;

        public CertificationDetailInfo(Certification certification, VerificationFlag isVerified) {
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

    @Getter
    @AllArgsConstructor
    public static class CertificationsByChallengeDate {
        MemberSimpleInfo memberSimpleInfo;
        CertificationInfo certificationInfo;

        public CertificationsByChallengeDate(Certification certification) {
            this.memberSimpleInfo = new MemberSimpleInfo(certification.getMember());
            this.certificationInfo = new CertificationInfo(certification);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class CertificationsByMemberDate {
        ChallengeTitleInfo challengeTitleInfo;
        CertificationInfo certificationInfo;

        public CertificationsByMemberDate(Certification certification) {
            this.challengeTitleInfo = new ChallengeTitleInfo(certification.getChallenge());
            this.certificationInfo = new CertificationInfo(certification);
        }
    }

}
