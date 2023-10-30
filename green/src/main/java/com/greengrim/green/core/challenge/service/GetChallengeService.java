package com.greengrim.green.core.challenge.service;

import com.greengrim.green.common.exception.BaseException;
import com.greengrim.green.common.exception.errorCode.ChallengeErrorCode;
import com.greengrim.green.core.challenge.Challenge;
import com.greengrim.green.core.challenge.dto.ChallengeResponseDto.ChallengeDetailInfo;
import com.greengrim.green.core.challenge.repository.ChallengeRepository;
import com.greengrim.green.core.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetChallengeService {

    private final ChallengeRepository challengeRepository;

    /**
     * 챌린지 상세 조회
     * TODO: @param member 를 이용해 차단 목록에 있다면 보여주지 않기
     */
    public ChallengeDetailInfo getChallengeDetail(Member member, Long id) {
        Challenge challenge = findByIdWithValidation(id);
        return new ChallengeDetailInfo(challenge);
    }

    private Challenge findByIdWithValidation(Long id) {
        return challengeRepository.findById(id)
                .orElseThrow(() -> new BaseException(ChallengeErrorCode.EMPTY_CHALLENGE));
    }
}
