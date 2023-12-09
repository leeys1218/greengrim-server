package com.greengrim.green.core.market.controller;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.core.market.dto.MarketRequestDto.RegisterNftToMarket;
import com.greengrim.green.core.market.service.RegisterMarketService;
import com.greengrim.green.core.member.Member;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterMarketController {

    private final RegisterMarketService registerMarketService;

    /**
     * 마켓에 NFT 등록하기 Api
     */
    @PostMapping("/member/market")
    public ResponseEntity<Integer> registerMarket(@CurrentMember Member member,
                                                  @RequestBody RegisterNftToMarket registerNftToMarket)
            throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        registerMarketService.registerMarket(member, registerNftToMarket);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
