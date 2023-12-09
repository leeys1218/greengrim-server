package com.greengrim.green.core.keyword.member;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordMemberRepository extends JpaRepository<KeywordMember, Long> {

  List<KeywordMember> findByMemberId(Long memberId);
}
