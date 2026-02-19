package com.uzumtech.court.repository;

import com.uzumtech.court.constant.enums.PenaltyStatus;
import com.uzumtech.court.entity.PenaltyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PenaltyRepository extends JpaRepository<PenaltyEntity, Long> {

    @Modifying
    @Query("update PenaltyEntity p set p.status = :status, p.updatedAt = CURRENT_TIMESTAMP WHERE p.id = :penaltyId ")
    void updateStatus(@Param("penaltyId") Long penaltyId, @Param("status") PenaltyStatus status);

    @Query("SELECT p FROM PenaltyEntity p LEFT JOIN p.offense o WHERE p.id = ?1 AND o.judge.id = ?2")
    Optional<PenaltyEntity> findByIdAndJudgeId(Long id, Long judgeId);

    Optional<PenaltyEntity> findByOffenseId(Long offenseId);

    boolean existsByOffenseId(Long offenseId);
}
