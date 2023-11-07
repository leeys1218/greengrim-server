package com.greengrim.green.core.challenge.service;

import com.greengrim.green.common.entity.dto.PageResponseDto;
import com.greengrim.green.common.exception.BaseException;
import com.greengrim.green.common.exception.errorCode.ChallengeErrorCode;
import com.greengrim.green.common.exception.errorCode.GlobalErrorCode;
import com.greengrim.green.core.challenge.Category;
import com.greengrim.green.core.challenge.Challenge;
import com.greengrim.green.core.challenge.dto.ChallengeResponseDto.ChallengeDetailInfo;
import com.greengrim.green.core.challenge.dto.ChallengeResponseDto.ChallengeSimpleInfo;
import com.greengrim.green.core.challenge.repository.ChallengeRepository;
import com.greengrim.green.core.member.Member;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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

    /**
     * 카테고리 별 챌린지 조회
     * TODO: @param member 를 이용해 차단 목록에 있다면 보여주지 않기
     */
    public PageResponseDto<List<ChallengeSimpleInfo>> getChallengesByCategory(
            Member member, Category category, int page, int size, String sort) {
        List<ChallengeSimpleInfo> challengeSimpleInfoList = new ArrayList<>();
        Page<Challenge> challenges = challengeRepository.findByCategoryAndStateIsTrue(category,  getPageable(page, size, sort));
        challenges.forEach(challenge ->
                        challengeSimpleInfoList.add(new ChallengeSimpleInfo(challenge)));

        return new PageResponseDto<>(challenges.getNumber(), challenges.hasNext(), challengeSimpleInfoList);
    }


    /**
     * 내가 만든 챌린지 조회
     */
    public PageResponseDto<List<ChallengeSimpleInfo>> getMyChallenges(
            Member member, int page, int size, String sort) {
        List<ChallengeSimpleInfo> challengeSimpleInfoList = new ArrayList<>();
        Page<Challenge> challenges = challengeRepository.findByMemberAndStateIsTrue(member, getPageable(page, size, sort));
        challenges.forEach(challenge ->
                challengeSimpleInfoList.add(new ChallengeSimpleInfo(challenge)));

        return new PageResponseDto<>(challenges.getNumber(), challenges.hasNext(), challengeSimpleInfoList);
    }

    public Challenge findByIdWithValidation(Long id) {
        return challengeRepository.findById(id)
                .orElseThrow(() -> new BaseException(ChallengeErrorCode.EMPTY_CHALLENGE));
    }

    private Pageable getPageable(int page, int size, String sort) {
        if (sort.equals("DESC")) { // 최신순
            return PageRequest.of(page, size, Direction.DESC);
        } else if (sort.equals("ASC")) { // 오래된순
            return PageRequest.of(page, size, Direction.ASC);
        } else {
            throw new BaseException(GlobalErrorCode.NOT_VALID_ARGUMENT_ERROR);
        }
    }

}
