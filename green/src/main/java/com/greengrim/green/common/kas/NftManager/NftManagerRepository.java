package com.greengrim.green.common.kas.NftManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NftManagerRepository extends JpaRepository<NftManager, Long> {

    NftManager findNftManagerById(Long manageId);

    boolean existsNftManagerById(Long id);
}
