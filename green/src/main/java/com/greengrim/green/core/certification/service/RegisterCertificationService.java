package com.greengrim.green.core.certification.service;

import com.greengrim.green.core.certification.Certification;
import com.greengrim.green.core.certification.dto.CertificationRequestDto.RegisterCertification;
import com.greengrim.green.core.certification.repository.CertificationRepository;
import com.greengrim.green.core.challenge.Challenge;
import com.greengrim.green.core.challenge.repository.ChallengeRepository;
import com.greengrim.green.core.challenge.service.GetChallengeService;
import com.greengrim.green.core.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterCertificationService {

    private final GetChallengeService getChallengeService;
    private final CertificationRepository certificationRepository;

    public void register(Member member, RegisterCertification registerCertification) {
        Challenge challenge = getChallengeService.findByIdWithValidation(
                registerCertification.getChallengeId());

        Certification certification = Certification.builder()
                .imgUrl(registerCertification.getImgUrl())
                .description(registerCertification.getDescription())
                .round(registerCertification.getRound())
                .validation(0)          // 상호 인증 성공 여부, 0=진행중
                .verificationCount(10)  // 남은 상호 인증 횟수
                .member(member)
                .challenge(challenge)
                .status(true)
                .build();

        certificationRepository.save(certification);
    }
}
