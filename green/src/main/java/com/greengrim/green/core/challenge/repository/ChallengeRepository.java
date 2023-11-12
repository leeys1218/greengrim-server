package com.greengrim.green.core.challenge.repository;

import com.greengrim.green.core.challenge.Category;
import com.greengrim.green.core.challenge.Challenge;
import com.greengrim.green.core.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    @Query(value = "SELECT c FROM Challenge c WHERE c.category=:category AND c.status=true")
    Page<Challenge> findByCategoryAndStateIsTrue(@Param("category") Category category, Pageable pageable);

    /**
     * 내가 만든 챌린지 조회 - 최신순, 오래된 순, 인원 많은 순, 인원 적은 순
     */
    @Query(value = "SELECT c FROM Challenge c WHERE c.member=:member AND c.status=true")
    Page<Challenge> findByMemberAndStateIsTrue(@Param("member") Member member, Pageable pageable);

    @Query(value = "SELECT c FROM Challenge c WHERE c.status=true ORDER BY c.headCount DESC")
    Page<Challenge> findHotChallenges(Pageable pageable);

    Challenge findByChatroomId(Long chatroomId);
}
