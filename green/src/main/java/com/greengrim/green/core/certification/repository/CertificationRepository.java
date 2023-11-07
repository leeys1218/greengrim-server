package com.greengrim.green.core.certification.repository;

import com.greengrim.green.core.certification.Certification;
import com.greengrim.green.core.challenge.Challenge;
import com.greengrim.green.core.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {

    int countsByMemberAndAndChallenge(
            @Param("member") Member member,
            @Param("challenge") Challenge challenge);
}
