package com.greengrim.green.core.wallet.controller;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.wallet.dto.WalletRequestDto;
import com.greengrim.green.core.wallet.dto.WalletRequestDto.CheckPassword;
import com.greengrim.green.core.wallet.dto.WalletResponseDto.ExistsWalletInfo;
import com.greengrim.green.core.wallet.dto.WalletResponseDto.WalletDetailInfo;
import com.greengrim.green.core.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    /**
     * [POST] 지갑 발급하기
     */
    @Operation(summary = "지갑 발급하기")
    @PostMapping("/visitor/wallets")
    public ResponseEntity<Integer> registerWallet(
            @Valid @RequestBody CheckPassword checkPassword, @CurrentMember Member member)
            throws IOException, ParseException, InterruptedException, java.text.ParseException {
        walletService.registerWallet(member, checkPassword);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

    /**
     * [GET] 지갑 정보 가져오기
     */
    @Operation(summary = "지갑 정보 가져오기")
    @GetMapping("/member/wallets")
    public ResponseEntity<WalletDetailInfo> getWalletDetail(@CurrentMember Member member)
            throws IOException, ParseException, InterruptedException, java.text.ParseException {
        return new ResponseEntity<>(walletService.getWalletDetail(member), HttpStatus.OK);
    }

    /**
     * [GET] 지갑 존재 여부 가져오기
     */
    @Operation(summary = "지갑 존재 여부 가져오기")
    @GetMapping("/visitor/wallets")
    public ResponseEntity<ExistsWalletInfo> existsWallet(@CurrentMember Member member) {
        return new ResponseEntity<>(walletService.existsWallet(member), HttpStatus.OK);
    }
}
