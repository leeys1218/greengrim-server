package com.greengrim.green.core.certification.controller;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.common.entity.dto.PageResponseDto;
import com.greengrim.green.core.certification.dto.CertificationResponseDto.CertificationDetailInfo;
import com.greengrim.green.core.certification.dto.CertificationResponseDto.CertificationsByChallengeDate;
import com.greengrim.green.core.certification.dto.CertificationResponseDto.CertificationsByMemberDate;
import com.greengrim.green.core.certification.dto.CertificationResponseDto.CertificationsByMonth;
import com.greengrim.green.core.certification.service.GetCertificationService;
import com.greengrim.green.core.member.Member;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetCertificationController {

    private final GetCertificationService getCertificationService;

    /**
     * [GET] 인증 상세 조회
     */
    @Operation(summary = "인증 상세 조회")
    @GetMapping("/certifications/{id}")
    public ResponseEntity<CertificationDetailInfo> getCertificationInfo(
            @CurrentMember Member member,
            @PathVariable("id") Long id) {
        return new ResponseEntity<>(
                getCertificationService.getCertificationInfo(member, id),
                HttpStatus.OK);
    }

    /**
     * [GET] 챌린지 별 인증 목록 조회 - MONTH
     */
    @Operation(summary = "챌린지 별 인증 내역 조회 - MONTH")
    @GetMapping("/certifications/month")
    public ResponseEntity<CertificationsByMonth> getCertificationsByChallengeMonth(
            @CurrentMember Member member,
            @RequestParam(value = "challengeId") Long challengeId) {
        return ResponseEntity.ok(getCertificationService.getCertificationsByChallengeMonth(
                member, challengeId));
    }

    /**
     * [GET] 챌린지 별 인증 목록 조회 - DATE
     */
    @Operation(summary = "챌린지 별 인증 내역 조회 - DATE")
    @GetMapping("/certifications/date")
    public ResponseEntity<PageResponseDto<List<CertificationsByChallengeDate>>> getCertificationsByChallengeDate(
            @CurrentMember Member member,
            @RequestParam(value = "challengeId") Long challengeId,
            @RequestParam(value = "date") String date,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size) { // 2023-11-07
        return ResponseEntity.ok(getCertificationService.getCertificationsByChallengeDate(
                member, challengeId, date, page, size));
    }

    /**
     * [GET] 멤버 별 인증 목록 조회 - MONTH
     */
    @Operation(summary = "내 챌린지 인증 내역 조회 - MONTH")
    @GetMapping("/visitor/certifications/month")
    public ResponseEntity<CertificationsByMonth> getCertificationsByChallengeMonth(
            @CurrentMember Member member) {
        return ResponseEntity.ok(getCertificationService.getCertificationsByMemberMonth(member));
    }

    /**
     * [GET] 멤버 별 인증 목록 조회 - DATE
     */
    @Operation(summary = "내 챌린지 인증 내역 조회 - DATE")
    @GetMapping("/visitor/certifications/date")
    public ResponseEntity<PageResponseDto<List<CertificationsByMemberDate>>> getCertificationsByChallengeDate(
            @CurrentMember Member member,
            @RequestParam(value = "date") String date,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size) { // 2023-11-07
        return ResponseEntity.ok(getCertificationService.getCertificationsByMemberDate(
                member, date, page, size));
    }

    /**
     * [GET] 상호 검증할 인증 상세 조회 - 출석체크
     */
    @Operation(summary = "상호 검증할 인증 상세 조회 - 출석체크")
    @GetMapping("/visitor/verifications")
    public ResponseEntity<CertificationDetailInfo> registerVerification(
            @CurrentMember Member member) {
        return new ResponseEntity<>(
                getCertificationService.getCertificationInfo(
                        member, getCertificationService.findCertificationForVerification(member))
                , HttpStatus.OK);
    }

}
