package com.greengrim.green.core.market.repository;

import com.greengrim.green.core.market.Market;
import com.greengrim.green.core.nft.Nft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {

    boolean existsByNft(Nft nft);
}
