package com.greengrim.green.core.certification.controller;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.core.certification.dto.CertificationRequestDto.RegisterCertification;
import com.greengrim.green.core.certification.service.RegisterCertificationService;
import com.greengrim.green.core.challenge.dto.ChallengeRequestDto;
import com.greengrim.green.core.member.Member;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
@RequiredArgsConstructor
public class RegisterCertificationController {

    private final RegisterCertificationService registerCertificationService;

    /**
     * [POST] 인증 생성
     */
    @PostMapping("/visitor/certifications")
    public ResponseEntity<Integer> registerCertification(
            @CurrentMember Member member,
            @Valid @RequestBody RegisterCertification registerCertification) {
        registerCertificationService.register(member, registerCertification);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
