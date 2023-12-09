package com.greengrim.green.core.nft.dto;

import static com.greengrim.green.common.entity.Time.calculateTime;

import com.greengrim.green.core.member.dto.MemberResponseDto.MemberSimpleInfo;
import com.greengrim.green.core.nft.Nft;
import java.util.List;
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
            this.imgUrl = nft.getGrim().getImgUrl();
            this.title = nft.getTitle();
            this.description = nft.getDescription();
            this.createdAt = calculateTime(nft.getCreatedAt(), 1);
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class NftDetailInfo {
        private MemberSimpleInfo memberSimpleInfo;
        private NftInfo nftInfo;
        private String contracts;
        private String tokenId;
        private String price;
        private boolean isMine;
        private boolean isMarketed;

        public NftDetailInfo(Nft nft, String price, boolean isMine) {
            this.memberSimpleInfo = new MemberSimpleInfo(nft.getMember());
            this.nftInfo = new NftInfo(nft);
            this.contracts = nft.getContracts();
            this.tokenId = nft.getNftId();
            this.price = price;
            this.isMine = isMine;
            this.isMarketed = nft.isMarketed();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class NftSimpleInfo {
        private Long id;
        private String imgUrl;
        private String title;

        public NftSimpleInfo(Nft nft) {
            this.id = nft.getId();
            this.imgUrl = nft.getGrim().getImgUrl();
            this.title = nft.getTitle();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class HomeNfts {
        private List<HomeNftInfo> homeNftInfos;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class HomeNftInfo {
        private NftSimpleInfo nftSimpleInfo;
        private MemberSimpleInfo memberSimpleInfo;
        private String price;

        public HomeNftInfo(Nft nft, String price) {
            this.nftSimpleInfo = new NftSimpleInfo(nft);
            this.memberSimpleInfo = new MemberSimpleInfo(nft.getMember());
            this.price = price;
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class NftInfoBeforeSale {
        private NftSimpleInfo nftSimpleInfo;
        private MemberSimpleInfo memberSimpleInfo;

        public NftInfoBeforeSale(Nft nft) {
            this.nftSimpleInfo = new NftSimpleInfo(nft);
            this.memberSimpleInfo = new MemberSimpleInfo(nft.getMember());
        }
    }

}
