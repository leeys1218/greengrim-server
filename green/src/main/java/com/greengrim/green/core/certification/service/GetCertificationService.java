package com.greengrim.green.core.certification.service;

import com.greengrim.green.common.exception.BaseException;
import com.greengrim.green.common.exception.errorCode.CertificationErrorCode;
import com.greengrim.green.core.certification.Certification;
import com.greengrim.green.core.certification.dto.CertificationResponseDto.CertificationDetailInfo;
import com.greengrim.green.core.certification.dto.CertificationResponseDto.CertificationsByMonth;
import com.greengrim.green.core.certification.repository.CertificationRepository;
import com.greengrim.green.core.challenge.Challenge;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.verification.VerificationService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCertificationService {

    private final VerificationService verificationService;
    private final CertificationRepository certificationRepository;

    /**
     * 인증 상세 보기
     */
    public CertificationDetailInfo getCertificationInfo(Member member, Long id) {
        Certification certification = certificationRepository.findById(id)
                .orElseThrow(() -> new BaseException(CertificationErrorCode.EMPTY_CHALLENGE));

        // 상호 인증에 참여했는가?
        boolean isVerified = true; // default 가 true
        if(member != null) {       // 로그인 했다면 조회해서 넣어줌
            verificationService.checkVerification(member.getId(), certification.getId());
        }

        return new CertificationDetailInfo(certification, isVerified);
    }

    /**
     * 멤버와 챌린지를 받아서 몇 회차 인증할 차례인지 반환
     */
    public int getRoundByMemberAndChallenge(Member member, Challenge challenge) {
        return certificationRepository.countsByMemberAndAndChallenge(member, challenge) + 1;
    }

    /**
     * 챌린지 월 별 인증 유무를 date 리스트 형식으로 반환
     */
    public CertificationsByMonth getCertificationsByChallengeMonth(
            Member member, Long challengeId, String month) {
        List<String> date = certificationRepository.findCertificationsByChallengeMonth(month, challengeId);
        return new CertificationsByMonth(date);
    }
}
