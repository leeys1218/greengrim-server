package com.greengrim.green.common.kas.NftManager;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NftManagerService {

    private final NftManagerRepository nftManagerRepository;

    @PostConstruct
    public void registerNftManager() {
        if(!nftManagerRepository.existsNftManagerById(1L)) {
            nftManagerRepository.save(new NftManager(0L));
        }
    }


    public Long getNftIdAndPlusOne() {
        NftManager nftManager = nftManagerRepository.findNftManagerById(1L);
        nftManager.upCount();
        return nftManager.getCount();
    }
}
