package com.greengrim.green.core.challenge.controller;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.core.challenge.dto.ChallengeRequestDto.RegisterChallenge;
import com.greengrim.green.core.challenge.service.RegisterChallengeService;
import com.greengrim.green.core.chatroom.dto.ChatroomResponseDto.ChatroomInfo;
import com.greengrim.green.core.member.Member;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterChallengeController {

    private final RegisterChallengeService registerChallengeService;

    /**
     * [POST] 챌린지 생성
     */
    @Operation(summary = "챌린지 생성")
    @PostMapping("/visitor/challenges")
    public ResponseEntity<ChatroomInfo> registerChallenge(@CurrentMember Member member,
            @Valid @RequestBody RegisterChallenge registerChallenge) {
        return ResponseEntity.ok(registerChallengeService.register(member, registerChallenge));
    }
}
