package com.greengrim.green.core.certification.service;

import com.greengrim.green.common.exception.BaseException;
import com.greengrim.green.common.exception.errorCode.CertificationErrorCode;
import com.greengrim.green.core.certification.Certification;
import com.greengrim.green.core.certification.dto.CertificationResponseDto.CertificationDetailInfo;
import com.greengrim.green.core.certification.repository.CertificationRepository;
import com.greengrim.green.core.challenge.Challenge;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.verification.VerificationService;
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

    public int getRoundByMemberAndChallenge(Member member, Challenge challenge) {
        return certificationRepository.countsByMemberAndAndChallenge(member, challenge) + 1;
    }
}
