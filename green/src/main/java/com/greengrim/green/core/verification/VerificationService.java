package com.greengrim.green.core.verification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationRepository verificationRepository;

    public boolean checkVerification(Long memberId, Long certificationId) {
        return verificationRepository.findByMemberIdAndCertificationId(
                memberId, certificationId).isPresent();
    }
}
