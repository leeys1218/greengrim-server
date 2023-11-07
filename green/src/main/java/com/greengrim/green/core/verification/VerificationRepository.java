package com.greengrim.green.core.verification;

import com.greengrim.green.core.verification.Verification;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, Long> {

    Optional<Verification> findByMemberIdAndCertificationId(
            @Param("memberId") Long memberId,
            @Param("certificationId") Long certificationId);
}
