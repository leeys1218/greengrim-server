package com.greengrim.green.core.nft.service;

import com.greengrim.green.common.exception.BaseException;
import com.greengrim.green.common.exception.errorCode.GrimErrorCode;
import com.greengrim.green.core.grim.Grim;
import com.greengrim.green.core.grim.repository.GrimRepository;
import com.greengrim.green.core.wallet.kas.KasProperties;
import com.greengrim.green.core.wallet.kas.KasService;
import com.greengrim.green.core.wallet.kas.NftManager.NftManagerService;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.nft.Nft;
import com.greengrim.green.core.nft.dto.NftRequestDto.RegisterNft;
import com.greengrim.green.core.nft.dto.NftResponseDto.NftId;
import com.greengrim.green.core.nft.repository.NftRepository;
import com.greengrim.green.core.wallet.Wallet;
import com.greengrim.green.core.wallet.service.WalletService;
import jakarta.transaction.Transactional;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class RegisterNftService {

    private final KasService kasService;
    private final KasProperties kasProperties;
    private final WalletService walletService;
    private final NftManagerService nftManagerService;
    private final NftRepository nftRepository;
    private final GrimRepository grimRepository;

    public Nft register(Member member, RegisterNft registerNft, String nftId,
                        String contracts, String txHash, String imgUrl, Grim grim) {
        Nft nft = Nft.builder()
                .nftId(nftId)
                .contracts(contracts)
                .txHash(txHash)
                .imgUrl(imgUrl)
                .title(registerNft.getTitle())
                .description(registerNft.getDescription())
                .reportCount(0)
                .status(true)
                .isMarketed(false)
                .member(member)
                .grim(grim)
                .build();
        nftRepository.save(nft);
        return nft;
    }

    public NftId registerNft(Member member, RegisterNft registerNft)
            throws IOException, ParseException, java.text.ParseException, InterruptedException {
        Grim grim = grimRepository.findByIdAndStatusIsTrue(registerNft.getGrimId())
                .orElseThrow(() -> new BaseException(GrimErrorCode.EMPTY_GRIM));

        Wallet wallet = member.getWallet();
        // 비밀번호 맞는지 확인 및 지갑 사용 처리
        walletService.checkPayPassword(wallet, registerNft.getPassword());
        walletService.useWallet(wallet);
        // MetaData 업로드하고 imgUrl 받아오기
        String imgUrl = kasService.uploadMetadata(registerNft);
        // GreenGrim 토큰으로 발행된 NFT는 모두 순서를 정해서 번호를 해야함! 우리는 그냥 10진수로 하자
        String nftId = "0x" + Long.toHexString(nftManagerService.getNftIdAndPlusOne());
        // Minting 하고 txHash 받아오기
        String txHash = kasService.createNft(wallet.getAddress(), nftId, imgUrl);
        // NFT 객체 생성하기
        Nft nft = register(member,
                registerNft,
                nftId,
                kasProperties.getNftContractAddress(),
                txHash,
                registerNft.getAsset(),
                grim
        );
        grim.setNft(nft);
        // TODO: Transaction 추가
        return new NftId(nft.getId());
    }
}
