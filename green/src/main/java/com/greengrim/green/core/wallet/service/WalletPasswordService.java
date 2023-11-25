package com.greengrim.green.core.wallet.service;

import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.wallet.dto.WalletRequestDto.CheckPassword;
import com.greengrim.green.core.wallet.dto.WalletResponseDto.CheckPasswordInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletPasswordService {

    private final WalletService walletService;

    public CheckPasswordInfo checkPassword(Member member, CheckPassword checkPassword) {
        return walletService.checkPayPassword(member.getWallet(), checkPassword.getPassword());
    }
}
