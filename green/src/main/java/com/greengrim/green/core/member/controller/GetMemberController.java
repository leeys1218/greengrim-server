package com.greengrim.green.core.member.controller;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.member.dto.MemberResponseDto.MemberInfo;
import com.greengrim.green.core.member.usecase.GetMemberUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetMemberController {

    private final GetMemberUseCase getMemberUseCase;

    /**
     * [GET] 내 프로필 조회
     */
    @GetMapping("/visitor/profile")
    public ResponseEntity<MemberInfo> getCurrentMemberInfo(
            @CurrentMember Member member) {
        return new ResponseEntity<>(getMemberUseCase.getMemberInfo(member),
                HttpStatus.OK);
    }
}
