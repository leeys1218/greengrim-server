package com.greengrim.green.core.verification;

import com.greengrim.green.common.exception.BaseException;
import com.greengrim.green.common.exception.errorCode.CertificationErrorCode;
import com.greengrim.green.core.certification.Certification;
import com.greengrim.green.core.certification.dto.CertificationRequestDto.RegisterCertification;
import com.greengrim.green.core.certification.repository.CertificationRepository;
import com.greengrim.green.core.certification.service.GetCertificationService;
import com.greengrim.green.core.challenge.Challenge;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.member.repository.MemberRepository;
import com.greengrim.green.core.verification.dto.VerificationRequest.RegisterVerification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final CertificationRepository certificationRepository;
    private final VerificationRepository verificationRepository;
    private final MemberRepository memberRepository;

    public void register(Member member, RegisterVerification registerVerification) {
        Certification certification = certificationRepository.findById(registerVerification.getCertificationId())
                .orElseThrow(() -> new BaseException(CertificationErrorCode.EMPTY_CHALLENGE));

        Verification verification = Verification.builder()
                .memberId(member.getId())
                .certificationId(registerVerification.getCertificationId())
                .response(registerVerification.isResponse())
                .build();

        verificationRepository.save(verification);

        // 상호 검증 처리하기
        verifyCertification(member.getId(), certification);
    }

    /**
     * 상호 검증이 과반수에 도달했을 때 처리하는 함수
     */
    public void verifyCertification(Long memberId, Certification certification) {
        // 이 인증에 거짓이라고 답한 사람이 5명 이상이라면, 해당 인증은 실패한 인증이다.
        // (상호 검증 중단, 인증 비활성화 처리, 포인트 뺏기, 탄소 감소량 뺏기)
        if(checkCertificationFailOrSuccess(memberId, certification.getId(), false)) {
            // 인증 entity 상호 검증 실패 처리
            certification.setValidationFail();
            Member lier = certification.getMember();
            // 포인트 뺏기
            lier.minusPoint(certification.getChallenge().getCategory().getPoint());
            // 탄소 감소량 뺏기
            lier.setCarbonReduction(-certification.getChallenge().getCategory().getCarbonReduction());
            memberRepository.save(lier);
        } // 진실이라고 답한 사람이 5명 이상이라면, 해당 인증은 성공한 인증이다. (상호 검증 중단)
        else if(checkCertificationFailOrSuccess(memberId, certification.getId(), true)) {
            certification.setValidationSuccess();
        }
        // 인증 entity 에서 남은 상호 검증 횟수 1 줄이기
        certification.minusVerificationCount();
    }

    /**
     * 상호 검증에 참여했는지 확인하는 함수
     */
    public boolean checkVerification(Long memberId, Long certificationId) {
        return verificationRepository.findByMemberIdAndCertificationId(
                memberId, certificationId).isPresent();
    }

    /**
     * 상호 검증이 과반수에 도달했는지 확인하는 함수
     */
    private boolean checkCertificationFailOrSuccess(Long memberId, Long certificationId, boolean response) {
        return verificationRepository.countByMemberIdAndCertificationIdAndResponse(
                memberId, certificationId, response) >= 5;
    }
}
