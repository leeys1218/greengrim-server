package com.greengrim.green.core.certification.service;

import com.greengrim.green.core.certification.Certification;
import com.greengrim.green.core.certification.dto.CertificationRequestDto.RegisterCertification;
import com.greengrim.green.core.certification.repository.CertificationRepository;
import com.greengrim.green.core.challenge.Challenge;
import com.greengrim.green.core.challenge.service.GetChallengeService;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterCertificationService {

    private final MemberRepository memberRepository;
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

        member.plusPoint(challenge.getCategory().getPoint());   // 10 포인트 추가
        member.setCarbonReduction(challenge.getCategory().getCarbonReduction());   // 탄소 저감량 추가
        memberRepository.save(member);

        certificationRepository.save(certification);
    }
}
