package com.greengrim.green.core.challenge.dto;

import static com.greengrim.green.common.entity.Time.calculateTime;

import com.greengrim.green.core.challenge.Category;
import com.greengrim.green.core.challenge.Challenge;
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

    public static class ChallengeSimpleInfo {
        private ChallengeInfo challengeInfo;
        private ChallengeSimpleTags challengeSimpleTags;

        public ChallengeSimpleInfo(Challenge challenge) {
            this.challengeInfo = new ChallengeInfo(challenge);
            this.challengeSimpleTags = new ChallengeSimpleTags(challenge);
        }
    }
}
