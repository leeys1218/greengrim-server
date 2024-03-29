package com.greengrim.green.core.nft.controller;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.nft.dto.NftRequestDto.RegisterNft;
import com.greengrim.green.core.nft.dto.NftResponseDto.NftId;
import com.greengrim.green.core.nft.service.RegisterNftService;
import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
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
    @Operation(summary = "NFT 생성하기")
    @PostMapping("/member/nfts")
    public ResponseEntity<NftId> registerNft(@CurrentMember Member member,
                                             @RequestBody RegisterNft registerNft)
            throws IOException, ParseException, InterruptedException, java.text.ParseException {
        return new ResponseEntity<>(registerNftService.registerNft(member, registerNft), HttpStatus.OK);
    }
}
