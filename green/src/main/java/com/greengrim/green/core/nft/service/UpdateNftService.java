package com.greengrim.green.core.nft.service;

import com.greengrim.green.common.exception.BaseException;
import com.greengrim.green.common.exception.errorCode.NftErrorCode;
import com.greengrim.green.core.grim.repository.GrimRepository;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.nft.Nft;
import com.greengrim.green.core.nft.repository.NftRepository;
import jakarta.transaction.Transactional;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class UpdateNftService {

    private final NftRepository nftRepository;
    private final GrimRepository grimRepository;

    @Transactional
    public void delete(Member member, Long id) {
        Nft nft = nftRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new BaseException(NftErrorCode.EMPTY_NFT));
        checkIsMine(member.getId(), nft.getMember().getId());
        checkIsMarketed(nft);

        nft.delete();
        nft.getGrim().setNft(null);
        grimRepository.save(nft.getGrim());
    }

    private void checkIsMine(Long viewerId, Long ownerId) {
        if(!Objects.equals(viewerId, ownerId)) {
            throw new BaseException(NftErrorCode.NO_AUTHORIZATION);
        }
    }

    private void checkIsMarketed(Nft nft) {
        if(nft.isMarketed()) {
           throw new BaseException(NftErrorCode.UNABLE_DELETE);
        }
    }
}
