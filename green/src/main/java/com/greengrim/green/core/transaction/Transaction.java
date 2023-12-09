package com.greengrim.green.core.transaction;

import com.greengrim.green.common.entity.BaseTime;
import com.greengrim.green.core.nft.Nft;
import com.greengrim.green.core.transaction.dto.TransactionRequestDto.TransactionSetDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @NotNull
    private Long buyerId;
    @NotNull
    private Long sellerId;

    @NotNull
    private Double payAmount;
    @NotNull
    private Double payBackAmount;

    @NotNull
    private String payTransaction;
    @NotNull
    private String payBackTransaction;
    @NotNull
    private String feeTransaction;


    @ManyToOne(fetch = FetchType.LAZY)
    private Nft nft;

    public void setTransactionSet(TransactionSetDto transactionSet) {
        this.payTransaction = transactionSet.getPayTransaction();
        this.payBackTransaction = transactionSet.getPayBackTransaction();
        this.feeTransaction = transactionSet.getFeeTransaction();
    }
}
