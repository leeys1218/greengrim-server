package com.greengrim.green.core.nft.service;

import static com.greengrim.green.common.entity.SortOption.ASC;
import static com.greengrim.green.common.entity.SortOption.DESC;

import com.greengrim.green.common.entity.SortOption;
import com.greengrim.green.common.entity.dto.PageResponseDto;
import com.greengrim.green.common.exception.BaseException;
import com.greengrim.green.common.exception.errorCode.GlobalErrorCode;
import com.greengrim.green.common.exception.errorCode.NftErrorCode;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.nft.Nft;
import com.greengrim.green.core.nft.dto.NftResponseDto.HomeNftInfo;
import com.greengrim.green.core.nft.dto.NftResponseDto.HomeNfts;
import com.greengrim.green.core.nft.dto.NftResponseDto.NftDetailInfo;
import com.greengrim.green.core.nft.repository.NftRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public HomeNfts getHomeNfts(Member member, int page, int size, SortOption sortOption) {
        Page<Nft> nfts = nftRepository.findHomeNfts(getPageable(page, size, sortOption));

        List<HomeNftInfo> homeNftInfoList = new ArrayList<>();
        nfts.forEach(nft ->
                homeNftInfoList.add(new HomeNftInfo(nft, getPrice(nft))));

        return new HomeNfts(homeNftInfoList);
    }

    /**
     * 핫 NFTS 더보기
     * TODO: @param member 를 이용해 차단 목록에 있다면 보여주지 않기
     */
    public PageResponseDto<List<HomeNftInfo>> getMoreHotNfts(Member member, int page, int size, SortOption sortOption) {
        Page<Nft> nfts = nftRepository.findHomeNfts(getPageable(page, size, sortOption));
        return makeNftsInfoList(nfts);
    }

    /**
     * 내 NFTS 더보기
     * TODO: @param member 를 이용해 차단 목록에 있다면 보여주지 않기
     */
    public PageResponseDto<List<HomeNftInfo>> getMyHotNfts(Member member, int page, int size, SortOption sortOption) {
        Page<Nft> nfts = nftRepository.findMyNfts(member, getPageable(page, size, sortOption));
        return makeNftsInfoList(nfts);
    }

    private PageResponseDto<List<HomeNftInfo>> makeNftsInfoList(Page<Nft> nfts) {
        List<HomeNftInfo> homeNftInfos = new ArrayList<>();
        nfts.forEach(nft ->
                homeNftInfos.add(new HomeNftInfo(nft, getPrice(nft))));

        return new PageResponseDto<>(nfts.getNumber(), nfts.hasNext(), homeNftInfos);
    }

    private String getPrice(Nft nft) {
        if(!nft.isMarketed()) {
            return "NOT SALE";
        } else {
            // TODO: Market Entity 추가 이후에, 가격을 double -> String으로 변환하는 로직 추가
            return "가격 여기에";
        }
    }

    private Pageable getPageable(int page, int size, SortOption sort) {
        if (sort == DESC) { // 최신순
            return PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        } else if (sort == ASC) { // 오래된순
            return PageRequest.of(page, size, Sort.Direction.ASC, "createdAt");
        } else {
            throw new BaseException(GlobalErrorCode.NOT_VALID_ARGUMENT_ERROR);
        }
    }

    private boolean checkIsMine(Long memberId, Long ownerId) {
        return Objects.equals(memberId, ownerId);
    }
}
