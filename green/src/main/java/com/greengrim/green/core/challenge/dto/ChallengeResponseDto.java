package com.greengrim.green.core.challenge.dto;

import static com.greengrim.green.common.entity.Time.calculateTime;

import com.greengrim.green.core.challenge.Category;
import com.greengrim.green.core.challenge.Challenge;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ChallengeResponseDto {

    @Getter
    @RequiredArgsConstructor
    public static class ChallengeInfo {
        private Long id;
        private String title;
        private String description;
        private String imgUrl;

        public ChallengeInfo(Challenge challenge) {
            this.id = challenge.getId();
            this.title = challenge.getTitle();
            this.description = challenge.getDescription();
            this.imgUrl = challenge.getImgUrl();
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class ChallengeTags {
        private Category category;
        private String ticketCount;
        private String goalCount;
        private String weekMinCount;
        private String keyword;
        private String participantCount;

        public ChallengeTags(Challenge challenge) {
            this.category = challenge.getCategory();
            this.goalCount = challenge.getGoalCountTag();
            this.ticketCount = challenge.getTicketCountTag();
            this.weekMinCount = challenge.getWeekMinCountTag();
            this.keyword = challenge.getKeywordTag();
            this.participantCount = challenge.getParticipantCountTag();
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class ChallengeSimpleTags {
        private Category category;
        private String ticketCount;
        private String goalCount;
        private String keyword;

        public ChallengeSimpleTags(Challenge challenge) {
            this.category = challenge.getCategory();
            this.goalCount = challenge.getGoalCountTag();
            this.ticketCount = challenge.getTicketCountTag();
            this.keyword = challenge.getKeywordTag();
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class ChallengeDetailInfo {
        private ChallengeInfo challengeInfo;
        private ChallengeTags challengeTags;
        private String createdAt;

        public ChallengeDetailInfo(Challenge challenge) {
            this.challengeInfo = new ChallengeInfo(challenge);
            this.challengeTags = new ChallengeTags(challenge);
            this.createdAt = calculateTime(challenge.getCreatedAt(), 1);
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class ChallengeSimpleInfo {
        private ChallengeInfo challengeInfo;
        private ChallengeSimpleTags challengeSimpleTags;

        public ChallengeSimpleInfo(Challenge challenge) {
            this.challengeInfo = new ChallengeInfo(challenge);
            this.challengeSimpleTags = new ChallengeSimpleTags(challenge);
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class HotChallengeInfo {
        private ChallengeInfo challengeInfo;
        private HotChallengeTags hotChallengeTags;

        public HotChallengeInfo(Challenge challenge) {
            this.challengeInfo = new ChallengeInfo(challenge);
            this.hotChallengeTags = new HotChallengeTags(challenge);
        }
    }

    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class HomeChallenges {
        private List<HotChallengeInfo> hotChallengeInfos;
    }

    @Getter
    @RequiredArgsConstructor
    public static class HotChallengeTags {
        private Category category;
        private String ticketCount;
        private String keyword;

        public HotChallengeTags(Challenge challenge) {
            this.category = challenge.getCategory();
            this.ticketCount = challenge.getTicketCountTag();
            this.keyword = challenge.getKeywordTag();
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class ChallengeInfoForCertification {
        private String title;
        private String description;
        private Category category;
        private String ticketCount;

        public ChallengeInfoForCertification(Challenge challenge) {
            this.title = challenge.getTitle();
            this.description = challenge.getDescription();
            this.category = challenge.getCategory();
            this.ticketCount = challenge.getTicketCountTag();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class ChallengeTitleInfo {
        private String title;
        private String imgUrl;
        private Category category;

        public ChallengeTitleInfo(Challenge challenge) {
            this.title = challenge.getTitle();
            this.imgUrl = challenge.getImgUrl();
            this.category = challenge.getCategory();
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class ChallengePreviewInfo {
        private int round;  // 몇 회차 인증할 차례인지
        private String title;
        private Category category;
        private String ticketCount;

        public ChallengePreviewInfo(Challenge challenge, int round) {
            this.round = round;
            this.title = challenge.getTitle();
            this.category = challenge.getCategory();
            this.ticketCount = challenge.getTicketCountTag();
        }
    }
}
