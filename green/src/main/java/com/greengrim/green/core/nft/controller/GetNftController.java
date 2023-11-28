package com.greengrim.green.core.nft.controller;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.nft.dto.NftResponseDto.NftDetailInfo;
import com.greengrim.green.core.nft.service.GetNftService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetNftController {

    private final GetNftService getNftService;

    /**
     * [GET] NFT 상세 조회
     */
    @Operation(summary = "NFT 상세 조회")
    @GetMapping("/nfts/{id}")
    public ResponseEntity<NftDetailInfo> getNftInfo(
            @CurrentMember Member member,
            @PathVariable("id") Long id) {
        return new ResponseEntity<>(
                getNftService.getNftDetailInfo(member, id),
                HttpStatus.OK);
    }
}
