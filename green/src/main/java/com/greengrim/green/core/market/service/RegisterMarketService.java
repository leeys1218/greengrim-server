package com.greengrim.green.core.market.service;


import com.greengrim.green.common.exception.BaseException;
import com.greengrim.green.common.exception.errorCode.MarketErrorCode;
import com.greengrim.green.common.exception.errorCode.NftErrorCode;
import com.greengrim.green.core.market.Market;
import com.greengrim.green.core.market.dto.MarketRequestDto.RegisterNftToMarket;
import com.greengrim.green.core.market.repository.MarketRepository;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.nft.Nft;
import com.greengrim.green.core.nft.repository.NftRepository;
import com.greengrim.green.core.wallet.Wallet;
import com.greengrim.green.core.wallet.service.WalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class RegisterMarketService {

    private final NftRepository nftRepository;
    private final MarketRepository marketRepository;
    private final WalletService walletService;

    public void save(Market market) {
        marketRepository.save(market);
    }

    public void registerMarket(Member member, RegisterNftToMarket registerNft) {
        Wallet wallet = member.getWallet();
        walletService.checkPayPassword(wallet,registerNft.getPayPassword());
        Nft nft = nftRepository.findByIdAndMemberAndStatusTrue(member, registerNft.getNftId())
                .orElseThrow(() -> new BaseException(NftErrorCode.EMPTY_NFT));
        if(marketRepository.existsByNft(nft)) {
            throw new BaseException(MarketErrorCode.ALREADY_IN_MARKET);
        }
        // 판매하기 시 수수료 및 무료횟수 부분 추가 필요
        checkPrice(registerNft.getPrice());
        Market market = Market.builder()
                .price(registerNft.getPrice())
                .nft(nft)
                .member(member)
                .status(true)
                .build();
        save(market);
        nft.setMarket(market);
    }

    private void checkPrice(double price) {
        if (price > 20000000 || price < 0.01) {
            throw new BaseException(MarketErrorCode.INVALID_PRICE);
        }
    }
}
