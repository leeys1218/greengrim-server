package com.greengrim.green.core.grim.repository;

import com.greengrim.green.core.grim.Grim;
import com.greengrim.green.core.member.Member;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GrimRepository extends JpaRepository<Grim, Long> {

    @Query(value = "SELECT g FROM Grim g WHERE g.status=true")
    Page<Grim> findAllStatusIsTrue(Pageable pageable);

    @Query(value = "SELECT g FROM Grim g WHERE g.member=:member AND g.status=true")
    Page<Grim> findByMemberStatusIsTrue(@Param("member") Member member, Pageable pageable);

    Optional<Grim> findByIdAndStatusIsTrue(Long id);
}
