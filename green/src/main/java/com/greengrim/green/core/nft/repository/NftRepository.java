package com.greengrim.green.core.nft.repository;

import com.greengrim.green.core.nft.Nft;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NftRepository extends JpaRepository<Nft, Long> {

    Optional<Nft> findByIdAndStatusTrue(Long id);
}
