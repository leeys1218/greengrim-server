package com.greengrim.green.common.kas.NftManager;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.N;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

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
        Long id = nftManager.getCount();
        nftManager.upCount();
        return id;
    }
}
