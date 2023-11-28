package com.greengrim.green.common.kas.NftManager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NftManagerService {

    private final NftManagerRepository nftManagerRepository;

    public Long getNftIdAndPlusOne() {
        NftManager nftManager = nftManagerRepository.findNftManagerById(1L);
        Long id = nftManager.getCount();
        nftManager.upCount();
        return id;
    }
}
