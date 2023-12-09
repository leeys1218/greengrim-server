package com.greengrim.green.core.keyword;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {

  @Query(value = "SELECT * FROM keyword order by RAND() limit 5",nativeQuery = true)
  List<Keyword> findAll();
}