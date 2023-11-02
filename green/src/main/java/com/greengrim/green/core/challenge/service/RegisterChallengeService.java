package com.greengrim.green.core.challenge.service;

import com.greengrim.green.core.challenge.Challenge;
import com.greengrim.green.core.challenge.dto.ChallengeRequestDto.RegisterChallenge;
import com.greengrim.green.core.challenge.repository.ChallengeRepository;
import com.greengrim.green.core.challenge.usecase.RegisterChallengeUseCase;
import com.greengrim.green.core.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterChallengeService implements RegisterChallengeUseCase {

    private final ChallengeRepository challengeRepository;

    @Override
    public void register(Member member, RegisterChallenge registerChallenge) {
        Challenge challenge = Challenge.builder()
                .category(registerChallenge.getCategory())
                .title(registerChallenge.getTitle())
                .description(registerChallenge.getDescription())
                .imgUrl(registerChallenge.getImgUrl())
                .goalCount(registerChallenge.getGoalCount())
                .ticketTotalCount(registerChallenge.getTicketTotalCount())
                .ticketCurrentCount(registerChallenge.getTicketTotalCount())
                .weekMinCount(registerChallenge.getWeekMinCount())
                .capacity(registerChallenge.getCapacity())
                .keyword(registerChallenge.getKeyword())
                .headCount(1)
                .status(true)
                .member(member)
                .build();

        challengeRepository.save(challenge);
    }
}
