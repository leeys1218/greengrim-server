package com.greengrim.green.core.market.controller;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.core.market.dto.MarketRequestDto.UpdateMarket;
import com.greengrim.green.core.market.service.UpdateMarketService;
import com.greengrim.green.core.member.Member;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateMarketController {

    private final UpdateMarketService updateMarketService;

    /**
     * [POST] NFT 구매하기
     */
    @PostMapping("/member/markets/nfts")
    public ResponseEntity<Integer> purchaseNftInMarket(@CurrentMember Member member,
                                                       @RequestBody UpdateMarket updateMarket)
            throws NoSuchPaddingException, IllegalBlockSizeException, IOException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, java.text.ParseException, InvalidKeyException, InterruptedException {
        updateMarketService.purchaseNftInMarket(member, updateMarket);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
