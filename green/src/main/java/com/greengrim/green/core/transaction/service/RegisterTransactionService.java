package com.greengrim.green.core.transaction.service;

import static com.greengrim.green.common.kas.KasConstants.MINTING_FEE;

import com.greengrim.green.core.transaction.Transaction;
import com.greengrim.green.core.transaction.TransactionType;
import com.greengrim.green.core.transaction.dto.TransactionRequestDto.MintingTransactionDto;
import com.greengrim.green.core.transaction.dto.TransactionRequestDto.PurchaseNftOnMarketTransactionDto;
import com.greengrim.green.core.transaction.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterTransactionService {

    private final TransactionRepository transactionRepository;

    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public void saveMintingTransaction(MintingTransactionDto mintingTransactionDto) {
        Transaction transaction = Transaction.builder()
                .type(TransactionType.MINTING)
                .buyerId(mintingTransactionDto.getBuyerId())
                .sellerId(null)
                .payAmount(MINTING_FEE)
                .payBackAmount(null)
                .nft(mintingTransactionDto.getNft())
                .build();
        transaction.setTransactionSet(mintingTransactionDto.getTransactionSetDto());
        save(transaction);
    }

    public void savePurchaseNftOnMarketTransaction(
            PurchaseNftOnMarketTransactionDto purchaseNftOnMarketTransactionDto) {
        Transaction transaction = Transaction.builder()
                .type(TransactionType.DEAL)
                .buyerId(purchaseNftOnMarketTransactionDto.getBuyerId())
                .sellerId(purchaseNftOnMarketTransactionDto.getSellerId())
                .payAmount(purchaseNftOnMarketTransactionDto.getCoin())
                .nft(purchaseNftOnMarketTransactionDto.getNft())
                .build();
        transaction.setTransactionSet(purchaseNftOnMarketTransactionDto.getTransactionSetDto());
        save(transaction);
    }
}
