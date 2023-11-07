package com.greengrim.green.core.certification.service;

import com.greengrim.green.common.entity.dto.PageResponseDto;
import com.greengrim.green.common.exception.BaseException;
import com.greengrim.green.common.exception.errorCode.CertificationErrorCode;
import com.greengrim.green.core.certification.Certification;
import com.greengrim.green.core.certification.dto.CertificationResponseDto.CertificationDetailInfo;
import com.greengrim.green.core.certification.dto.CertificationResponseDto.CertificationsByChallengeDate;
import com.greengrim.green.core.certification.dto.CertificationResponseDto.CertificationsByMemberDate;
import com.greengrim.green.core.certification.dto.CertificationResponseDto.CertificationsByMonth;
import com.greengrim.green.core.certification.repository.CertificationRepository;
import com.greengrim.green.core.challenge.Challenge;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.verification.VerificationService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    /**
     * 멤버 월 별 인증 유무를 date 리스트 형식으로 반환
     */
    public CertificationsByMonth getCertificationsByMemberMonth(
            Member member, String month) {
        List<String> date = certificationRepository.findCertificationsByMemberMonth(month, member.getId());
        return new CertificationsByMonth(date);
    }

    /**
     * 챌린지 날짜 별 인증 반환
     */
    public PageResponseDto<List<CertificationsByChallengeDate>> getCertificationsByChallengeDate(
            Member member, Long challengeId, String date, int page, int size) {
        List<CertificationsByChallengeDate> certificationsByChallengeDates = new ArrayList<>();

        Page<Certification> certifications = certificationRepository.findCertificationsByChallengeDate(
                date, challengeId, PageRequest.of(page, size));

        certifications.forEach(certification ->
                certificationsByChallengeDates.add(new CertificationsByChallengeDate(certification)));

        return  new PageResponseDto<>(certifications.getNumber(), certifications.hasNext(), certificationsByChallengeDates);
    }

    /**
     * 멤버 날짜 별 인증 반환
     */
    public PageResponseDto<List<CertificationsByMemberDate>> getCertificationsByMemberDate(
            Member member, String date, int page, int size) {
        List<CertificationsByMemberDate> certificationsByMemberDates = new ArrayList<>();

        Page<Certification> certifications = certificationRepository.findCertificationsByMemberDate(
                date, member.getId(), PageRequest.of(page, size));

        certifications.forEach(certification ->
                certificationsByMemberDates.add(new CertificationsByMemberDate(certification)));

        return  new PageResponseDto<>(certifications.getNumber(), certifications.hasNext(), certificationsByMemberDates);
    }
}
