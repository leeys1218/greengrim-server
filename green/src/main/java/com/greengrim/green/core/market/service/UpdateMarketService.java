package com.greengrim.green.core.market.service;

import com.greengrim.green.common.exception.BaseException;
import com.greengrim.green.common.exception.errorCode.MarketErrorCode;
import com.greengrim.green.core.market.Market;
import com.greengrim.green.core.market.dto.MarketRequestDto.UpdateMarket;
import com.greengrim.green.core.market.repository.MarketRepository;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.wallet.service.WalletService;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateMarketService {

    private final MarketRepository marketRepository;
    private final WalletService walletService;

    public void purchaseNftInMarket(Member buyer, UpdateMarket updateMarket)
            throws NoSuchPaddingException, IllegalBlockSizeException, IOException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, java.text.ParseException, InvalidKeyException, InterruptedException {
        Market market = marketRepository.findMarketByIdAndStatusTrue(updateMarket.getMarketId())
                .orElseThrow(() -> new BaseException(MarketErrorCode.EMPTY_MARKET));
        if (Objects.equals(market.getMember().getId(), buyer.getId())) {
            throw new BaseException(MarketErrorCode.UNABLE_PURCHASE);
        }
        walletService.purchaseNftInMarket(buyer, updateMarket.getPayPassword(), market);
        // TODO: 판매 알림 보내기
    }
}
