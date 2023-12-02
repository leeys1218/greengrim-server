package com.greengrim.green.core.nft.service;

import com.greengrim.green.common.exception.BaseException;
import com.greengrim.green.common.exception.errorCode.NftErrorCode;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.nft.Nft;
import com.greengrim.green.core.nft.dto.NftResponseDto.NftDetailInfo;
import com.greengrim.green.core.nft.repository.NftRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetNftService {

    private final NftRepository nftRepository;

    public NftDetailInfo getNftDetailInfo(Member member, Long id) {
        Nft nft = nftRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new BaseException(NftErrorCode.EMPTY_NFT));

        String price = getPrice(nft);

        boolean isMine = false;
        if(member!= null) { // 로그인 했다면
            isMine = checkIsMine(member.getId(), nft.getMember().getId());
        }
        return new NftDetailInfo(nft, price, isMine);
    }

    private String getPrice(Nft nft) {
        if(!nft.isMarketed()) {
            return "NOT SALE";
        } else {
            // TODO: Market Entity 추가 이후에, 가격을 double -> String으로 변환하는 로직 추가
            return "가격 여기에";
        }
    }

    private boolean checkIsMine(Long memberId, Long ownerId) {
        return Objects.equals(memberId, ownerId);
    }
}
