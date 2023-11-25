package com.greengrim.green.core.wallet.controller;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.wallet.dto.WalletRequestDto;
import com.greengrim.green.core.wallet.dto.WalletResponseDto.CheckPasswordInfo;
import com.greengrim.green.core.wallet.service.WalletPasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WalletPasswordController {

    private final WalletPasswordService walletPasswordService;

    /**
     * [POST] 비밀번호 확인
     */
    @PostMapping("/member/wallets/password")
    public ResponseEntity<CheckPasswordInfo> checkPwd(
            @CurrentMember Member member,
            @Valid @RequestBody WalletRequestDto.CheckPassword checkPassword) {
        return new ResponseEntity<>(walletPasswordService.checkPassword(member, checkPassword),
                HttpStatus.OK);
    }
}
