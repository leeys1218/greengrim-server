package com.greengrim.green.core.member.controller;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.member.dto.MemberRequestDto.ModifyProfile;
import com.greengrim.green.core.member.dto.MemberResponseDto;
import com.greengrim.green.core.member.usecase.UpdateMemberUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * [PATCH] 프로필 수정
     */
    @PatchMapping("/visitor/profile")
    public ResponseEntity<Integer> modifyProfile(
            @CurrentMember Member member,
            @RequestBody ModifyProfile modifyProfile) {
        updateMemberUseCase.modifyProfile(member, modifyProfile);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * [DELETE] 회원 탈퇴
     */
    @DeleteMapping("/visitor/delete")
    public ResponseEntity<Integer> deleteMember(
            @CurrentMember Member member) {
        updateMemberUseCase.deleteMember(member);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
