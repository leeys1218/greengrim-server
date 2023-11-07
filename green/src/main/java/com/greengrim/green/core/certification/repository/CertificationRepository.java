package com.greengrim.green.core.certification.repository;

import com.greengrim.green.core.certification.Certification;
import com.greengrim.green.core.challenge.Challenge;
import com.greengrim.green.core.member.Member;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {

    int countsByMemberAndAndChallenge(
            @Param("member") Member member,
            @Param("challenge") Challenge challenge);

    @Query("SELECT c FROM Certification c WHERE date_format(c.createdAt, '%Y-%m-%d')  = :date and c.member.id = :memberId")
    Page<Certification> findCertificationsByDate(@Param("date") String date, @Param("memberId") Long memberId, Pageable pageable);

    @Query("SELECT date_format(c.createdAt, '%Y-%m-%d') FROM Certification c WHERE date_format(c.createdAt, '%Y-%m') = :month and c.challenge.id = :challengeId")
    List<String> findCertificationsByChallengeMonth(@Param("month") String month, @Param("challengeId") Long challengeId);

    @Query("SELECT date_format(c.createdAt, '%Y-%m-%d') FROM Certification c WHERE date_format(c.createdAt, '%Y-%m') = :month and c.member.id = :memberId")
    List<String> findCertificationsByMemberMonth(@Param("month") String month, @Param("memberId") Long memberId);

    @Query("SELECT c FROM Certification c WHERE date_format(c.createdAt, '%Y-%m-%d') = :date and c.challenge.id = :challengeId")
    Page<Certification> findCertificationsByChallengeDate(@Param("date") String date, @Param("challengeId") Long challengeId, Pageable pageable);

    @Query("SELECT c FROM Certification c WHERE date_format(c.createdAt, '%Y-%m-%d') = :date and c.member.id = :memberId")
    Page<Certification> findCertificationsByMemberDate(@Param("date") String date, @Param("memberId") Long memberId, Pageable pageable);
}
