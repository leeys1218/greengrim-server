package com.greengrim.green.core.grim;

import com.greengrim.green.common.entity.BaseTime;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.nft.Nft;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
public class Grim extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 1, max = 30)
    private String title;
    @NotNull
    private String imgUrl;
    @NotNull
    private boolean status;

    @ManyToOne
    private Member member;

    @OneToOne
    private Nft nft;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNft(Nft nft) {
        this.nft = nft;
    }
}
