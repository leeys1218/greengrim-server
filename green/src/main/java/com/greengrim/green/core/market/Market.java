package com.greengrim.green.core.market;

import com.greengrim.green.common.entity.BaseTime;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.nft.Nft;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
public class Market extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private double price;

    @NotNull
    private boolean status;

    @OneToOne(fetch = FetchType.LAZY)
    private Nft nft;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public void delete() {
        this.status = false;
    }
}
