package com.greengrim.green.core.member.controller;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.member.dto.MemberResponseDto;
import com.greengrim.green.core.member.usecase.UpdateMemberUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateMemberController {

    private final UpdateMemberUseCase updateMemberUseCase;

    /**
     * [PATCH] 로그인 토큰 갱신
     */
    @PatchMapping("/visitor/refresh")
    public ResponseEntity<MemberResponseDto.TokenInfo> refreshLogin(
            @CurrentMember Member member) {
        return new ResponseEntity<>(updateMemberUseCase.refreshAccessToken(member),
                HttpStatus.OK);
    }
}
