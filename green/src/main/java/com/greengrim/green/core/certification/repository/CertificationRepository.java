package com.greengrim.green.core.certification.repository;

import com.greengrim.green.core.certification.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {

}
