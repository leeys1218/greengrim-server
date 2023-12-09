package com.greengrim.green.core.member.controller;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.member.dto.MemberResponseDto.HomeInfo;
import com.greengrim.green.core.member.dto.MemberResponseDto.MemberInfo;
import com.greengrim.green.core.member.dto.MemberResponseDto.MyInfo;
import com.greengrim.green.core.member.service.GetMemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetMemberController {

    private final GetMemberService getMemberService;

    /**
     * [GET] 내 프로필 조회
     */
    @Operation(summary = "내 프로필 조회")
    @GetMapping("/visitor/profile")
    public ResponseEntity<MemberInfo> getCurrentMemberInfo(
            @CurrentMember Member member) {
        return new ResponseEntity<>(getMemberService.getMemberInfo(member),
                HttpStatus.OK);
    }

    /**
     * [GET] 내 탄소 저감량 조회
     */
    @Operation(summary = "내 탄소 저감량 조회")
    @GetMapping("/visitor/home")
    public ResponseEntity<HomeInfo> getHomeInfo(
            @CurrentMember Member member) {
        return new ResponseEntity<>(getMemberService.getHomeInfo(member),
                HttpStatus.OK);
    }

    /**
     * [GET] 더보기 조회
     */
    @Operation(summary = "더보기 조회")
    @GetMapping("/visitor/my")
    public ResponseEntity<MyInfo> getMyDetailInfo(
            @CurrentMember Member member) {
        return new ResponseEntity<>(getMemberService.getMyDetailInfo(member),
                HttpStatus.OK);
    }
}
