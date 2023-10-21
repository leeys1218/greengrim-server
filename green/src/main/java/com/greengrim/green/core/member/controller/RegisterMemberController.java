package com.greengrim.green.core.member.controller;

import com.greengrim.green.core.member.dto.MemberRequestDto;
import com.greengrim.green.core.member.dto.MemberResponseDto;
import com.greengrim.green.core.member.usecase.RegisterMemberUseCase;
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
public class RegisterMemberController {

    private final RegisterMemberUseCase registerMemberUseCase;

    /**
     * [POST] 소셜 회원가입
     * /sign-up
     */
    @Operation(summary = "소셜 회원가입")
    @PostMapping("/sign-up")
    public ResponseEntity<MemberResponseDto.TokenInfo> register(
            @Valid @RequestBody MemberRequestDto.RegisterMember registerMember) {
        return new ResponseEntity<>(registerMemberUseCase.registerMember(registerMember),
                HttpStatus.OK);
    }

    /**
     * [POST] 로그인
     * /login
     */
    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<MemberResponseDto.TokenInfo> login(
            @Valid @RequestBody MemberRequestDto.LoginMember loginMember) {
        return new ResponseEntity<>(registerMemberUseCase.login(loginMember),
                HttpStatus.OK);
    }
}
