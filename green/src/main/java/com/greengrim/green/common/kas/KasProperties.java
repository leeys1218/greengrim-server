package com.greengrim.green.common.kas;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class KasProperties {
    // 헤더에 넣을 권한
    @Value("${kas.authorization}")
    private String authorization;
    // 테스트넷 버전
    @Value("${kas.version}")
    private String version;
    // NFT Contract 이름
    @Value("${kas.nft-contract}")
    private String nftContract;
    // NFT Contract 주소
    @Value("${kas.nft-contract-address}")
    private String nftContractAddress;
    // 수수료를 받아놓는 계좌
    @Value("${kas.fee-account}")
    private String feeAccount;
    // KAS에 수수료를 대납해주는 계좌
    @Value("${kas.fee-payer-account}")
    private String feePayerAccount;

}
