package com.greengrim.green.core.nft.dto;

import static com.greengrim.green.common.entity.Time.calculateTime;

import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.member.dto.MemberResponseDto.MemberSimpleInfo;
import com.greengrim.green.core.nft.Nft;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class NftResponseDto {

    @Getter
    @AllArgsConstructor
    public static class NftId {
        private Long id;
    }

    @Getter
    @AllArgsConstructor
    public static class NftInfo {
        private Long id;
        private String imgUrl;
        private String title;
        private String description;
        private String createdAt;

        public NftInfo(Nft nft) {
            this.id = nft.getId();
            this.imgUrl = nft.getImgUrl();
            this.title = nft.getTitle();
            this.description = nft.getDescription();
            this.createdAt = calculateTime(nft.getCreatedAt(), 1);
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class NftDetailInfo {
        MemberSimpleInfo memberSimpleInfo;
        NftInfo nftInfo;
        private String contracts;
        private String tokenId;
        private String price;
        private boolean isMine;
        private boolean isMarketed;

        public NftDetailInfo(Nft nft, String price, boolean isMine) {
            memberSimpleInfo = new MemberSimpleInfo(nft.getMember());
            nftInfo = new NftInfo(nft);
            this.contracts = nft.getContracts();
            this.tokenId = nft.getNftId();
            this.price = price;
            this.isMine = isMine;
            this.isMarketed = nft.isMarketed();

        }
    }
}
