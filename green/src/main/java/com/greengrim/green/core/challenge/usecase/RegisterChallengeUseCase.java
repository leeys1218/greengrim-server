package com.greengrim.green.core.challenge.usecase;

import com.greengrim.green.core.challenge.dto.ChallengeRequestDto.RegisterChallenge;
import com.greengrim.green.core.member.Member;

public interface RegisterChallengeUseCase {

    void register(Member member, RegisterChallenge registerChallenge);
}
