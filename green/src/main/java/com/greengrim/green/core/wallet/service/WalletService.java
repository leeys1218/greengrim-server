package com.greengrim.green.core.wallet.service;

import static com.greengrim.green.common.kas.KasConstants.PURCHASE_FEE;

import com.greengrim.green.common.exception.BaseException;
import com.greengrim.green.common.exception.errorCode.WalletErrorCode;
import com.greengrim.green.common.kas.KasService;
import com.greengrim.green.common.password.BcryptService;
import com.greengrim.green.core.market.Market;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.member.Role;
import com.greengrim.green.core.member.service.RegisterMemberService;
import com.greengrim.green.core.nft.Nft;
import com.greengrim.green.core.transaction.dto.TransactionRequestDto.PurchaseNftOnMarketTransactionDto;
import com.greengrim.green.core.transaction.dto.TransactionRequestDto.TransactionSetDto;
import com.greengrim.green.core.transaction.service.RegisterTransactionService;
import com.greengrim.green.core.wallet.Wallet;
import com.greengrim.green.core.wallet.dto.WalletRequestDto.CheckPassword;
import com.greengrim.green.core.wallet.dto.WalletResponseDto.CheckPasswordInfo;
import com.greengrim.green.core.wallet.dto.WalletResponseDto.ExistsWalletInfo;
import com.greengrim.green.core.wallet.dto.WalletResponseDto.WalletDetailInfo;
import com.greengrim.green.core.wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    private final RegisterMemberService registerMemberService;
    private final RegisterTransactionService registerTransactionService;
    private final BcryptService bcryptService;
    private final KasService kasService;

    public void register(Member member, String password)
            throws IOException, ParseException, InterruptedException, java.text.ParseException {
        String address = kasService.createAccount();
        Wallet wallet = Wallet.builder()
                .address(address)
                .password(password)
                .wrongCount(0)
                .build();
        walletRepository.save(wallet);
        member.changeRole(Role.ROLE_MEMBER);
        member.changeWallet(wallet);
        registerMemberService.save(member);
    }

    /**
     * 지갑 생성하기
     */
    public void registerWallet(Member member, CheckPassword checkPassword)
            throws IOException, ParseException, java.text.ParseException, InterruptedException {
        register(member, bcryptService.encrypt(checkPassword.getPassword()));
    }

    /**
     * 비밀번호 확인
     */
    public CheckPasswordInfo checkPayPassword(Wallet wallet, String password) {
        if (wallet.getWrongCount() == 5) {
            throw new BaseException(WalletErrorCode.FIVE_WRONG_PASSWORD);
        }

        boolean matched = false;
        if (bcryptService.isMatch(password, wallet.getPassword())) {
            wallet.initWrongCount();
            matched = true;
        } else {
            wallet.plusWrongCount();
        }

        walletRepository.save(wallet);
        return new CheckPasswordInfo(matched, wallet.getWrongCount());
    }

    /**
     * 지갑 사용하기
     */
    public void useWallet(Wallet wallet) {
        if (Duration.between(wallet.getUpdatedAt(), LocalDateTime.now()).getSeconds() < 180) {
            throw new BaseException(WalletErrorCode.FAILED_USE_WALLET);
        } else {
            wallet.updateEntity();
            walletRepository.save(wallet);
        }
    }

    /**
     * 지갑 존재 유무 조회하기
     */
    public ExistsWalletInfo existsWallet(Member member) {
        return new ExistsWalletInfo(member.getWallet() != null);
    }

    /**
     * 지갑 정보 조회하기
     */
    public WalletDetailInfo getWalletDetail(Member member)
            throws IOException, ParseException, InterruptedException, java.text.ParseException {
        Wallet wallet = member.getWallet();
        return new WalletDetailInfo(wallet.getAddress(), kasService.getKlay(wallet));
    }

    /**
     * Klay 잔고 확인하기
     */
    private void checkKlay(Wallet wallet, double coin)
            throws IOException, ParseException, InterruptedException, java.text.ParseException {
        if (kasService.getKlay(wallet) < coin) {
            throw new BaseException(WalletErrorCode.NOT_ENOUGH_COIN);
        }
    }

    /**
     * NFT 구매 처리하기
     */
    public void purchaseNftInMarket(Member buyer, String payPassword, Market market)
            throws NoSuchPaddingException, IllegalBlockSizeException, IOException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, java.text.ParseException, InvalidKeyException, InterruptedException {
        Member seller = market.getMember();
        checkForDeal(buyer.getWallet(), payPassword, market.getPrice());
        purchaseNftOnMarketFun(buyer, seller, market);
    }

    private void checkForDeal(Wallet buyerWallet, String payPassword, double price)
            throws IOException, ParseException, InterruptedException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException, java.text.ParseException {
        checkWallet(buyerWallet, payPassword);
        checkKlay(buyerWallet, price);
    }

    private void purchaseNftOnMarketFun(Member buyer, Member seller, Market market)
            throws IOException, ParseException, InterruptedException, java.text.ParseException {
        Nft nft = market.getNft();
        Wallet buyerWallet = buyer.getWallet();
        Wallet sellerWallet = seller.getWallet();

        String sendFee = null;
        if (PURCHASE_FEE != 0D) {
            sendFee = kasService.sendKlayToFeeAccount(buyerWallet, PURCHASE_FEE);
        }
        String sendKlay = kasService.sendKlayToSeller(buyerWallet, sellerWallet,
                market.getPrice() - PURCHASE_FEE);
        String sendNft = kasService.sendNft(sellerWallet, buyerWallet, nft);
        registerTransactionService.savePurchaseNftOnMarketTransaction(
                new PurchaseNftOnMarketTransactionDto(buyer.getId(), seller.getId(),
                        market.getPrice() - PURCHASE_FEE, nft,
                        new TransactionSetDto(sendKlay, sendNft, sendFee))
        );
        nft.setMember(buyer);
        nft.setMarket(null);
        market.delete();
    }

    private void checkWallet(Wallet wallet, String payPassword) {
        checkPayPassword(wallet, payPassword);
        useWallet(wallet);
    }

}
