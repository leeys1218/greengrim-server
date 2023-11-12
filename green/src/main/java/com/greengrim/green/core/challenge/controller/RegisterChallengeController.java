package com.greengrim.green.core.challenge.controller;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.core.challenge.dto.ChallengeRequestDto.RegisterChallenge;
import com.greengrim.green.core.challenge.usecase.RegisterChallengeUseCase;
import com.greengrim.green.core.member.Member;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterChallengeController {

    private final RegisterChallengeUseCase registerChallengeUseCase;

    /**
     * [POST] 챌린지 생성
     */
    @Operation(summary = "챌린지 생성")
    @PostMapping("/visitor/challenges")
    public ResponseEntity<Integer> registerChallenge(@CurrentMember Member member,
            @Valid @RequestBody RegisterChallenge registerChallenge) {
        registerChallengeUseCase.register(member, registerChallenge);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
