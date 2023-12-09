package com.greengrim.green.core.market.repository;

import com.greengrim.green.core.market.Market;
import com.greengrim.green.core.nft.Nft;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {

    boolean existsByNft(Nft nft);

    @Query(value = "SELECT m FROM Market m LEFT JOIN FETCH m.nft WHERE m.id=:id AND m.status = true")
    Optional<Market> findMarketByIdAndStatusTrue(@Param("id") Long id);
}
