package com.greengrim.green.core.certification.controller;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.common.entity.SortOption;
import com.greengrim.green.common.entity.dto.PageResponseDto;
import com.greengrim.green.core.certification.Certification;
import com.greengrim.green.core.certification.dto.CertificationResponseDto.CertificationDetailInfo;
import com.greengrim.green.core.certification.dto.CertificationResponseDto.CertificationInfo;
import com.greengrim.green.core.certification.service.GetCertificationService;
import com.greengrim.green.core.challenge.Category;
import com.greengrim.green.core.challenge.dto.ChallengeResponseDto.ChallengeDetailInfo;
import com.greengrim.green.core.challenge.dto.ChallengeResponseDto.ChallengeSimpleInfo;
import com.greengrim.green.core.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetCertificationController {

    private final GetCertificationService getCertificationService;

    /**
     * [GET] 인증 상세 조회
     */
    @GetMapping("/certifications/{id}")
    public ResponseEntity<CertificationDetailInfo> getCertificationInfo(
            @CurrentMember Member member,
            @PathVariable("id") Long id) {
        return new ResponseEntity<>(
                getCertificationService.getCertificationInfo(member, id),
                HttpStatus.OK);
    }


}
