package com.greengrim.green.core.nft.controller;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.nft.dto.NftRequestDto.RegisterNft;
import com.greengrim.green.core.nft.dto.NftResponseDto.NftId;
import com.greengrim.green.core.nft.service.RegisterNftService;
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
public class RegisterNftController {

    private final RegisterNftService registerNftService;

    /**
     * [POST] NFT 생성
     */
    @PostMapping("/member/nfts")
    public ResponseEntity<NftId> registerNft(@CurrentMember Member member,
                                             @RequestBody RegisterNft registerNft)
            throws IOException, ParseException, InterruptedException, java.text.ParseException {
        return new ResponseEntity<>(registerNftService.registerNft(member, registerNft), HttpStatus.OK);
    }
}
