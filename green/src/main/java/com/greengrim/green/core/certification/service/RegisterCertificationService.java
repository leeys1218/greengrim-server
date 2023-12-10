package com.greengrim.green.core.certification.service;

import com.greengrim.green.common.fcm.FcmService;
import com.greengrim.green.core.certification.Certification;
import com.greengrim.green.core.certification.dto.CertificationRequestDto.RegisterCertification;
import com.greengrim.green.core.certification.dto.CertificationResponseDto.registerCertificationResponse;
import com.greengrim.green.core.certification.repository.CertificationRepository;
import com.greengrim.green.core.challenge.Challenge;
import com.greengrim.green.core.challenge.service.GetChallengeService;
import com.greengrim.green.core.keyword.KeywordService;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterCertificationService {

    private final GetChallengeService getChallengeService;
    private final KeywordService keywordService;
    private final FcmService fcmService;

    private final MemberRepository memberRepository;
    private final CertificationRepository certificationRepository;

    /**
     * @Todo: 응답에 챌린지 성공 유무 반환
     */
    public registerCertificationResponse register(Member member, RegisterCertification registerCertification) {
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

        // 인증 성공
        successCertification(member, challenge);
        // 챌린지 성공했으면 보상 제공
        successChallenge(challenge, certification);
        fcmService.sendSuccessCertification(member, certification, registerCertification.getChatroomId());

        return new registerCertificationResponse(certification);
    }

    /**
     * 인증 성공, 포인트와 탄소 저감량 제공
     */
    public void successCertification(Member member, Challenge challenge) {
        member.plusPoint(challenge.getCategory().getPoint());   // 포인트 추가
        member.setCarbonReduction(challenge.getCategory().getCarbonReduction());   // 탄소 저감량 추가
        memberRepository.save(member);
    }

    /**
     * 챌린지 성공, 키워드 제공
     */
    public void successChallenge(Challenge challenge, Certification certification) {
        if(challenge.getGoalCount() == certification.getRound()) {
            // 키워드 획득
            keywordService.addMemberKeyword(challenge.getKeyword(), certification.getMember());
            // 챌린지 티켓 하나 감소
            challenge.minusTicketCurrentCount();
        }
    }
}
