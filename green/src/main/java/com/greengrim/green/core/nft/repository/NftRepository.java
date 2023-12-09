package com.greengrim.green.core.nft.repository;

import com.greengrim.green.core.nft.Nft;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NftRepository extends JpaRepository<Nft, Long> {

    Optional<Nft> findByIdAndStatusTrue(Long id);

    @Query(value = "SELECT n FROM Nft n WHERE n.status=true ORDER BY n.createdAt DESC")
    Page<Nft> findHomeNfts(Pageable pageable);
}
