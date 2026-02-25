package com.uzumtech.court.repository;

import com.uzumtech.court.constant.enums.OffenseStatus;
import com.uzumtech.court.constant.enums.PenaltyStatus;
import com.uzumtech.court.entity.OffenseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface OffenseRepository extends JpaRepository<OffenseEntity, Long> {

    @Query("SELECT COUNT(e) FROM OffenseEntity e WHERE DATE(e.createdAt) = ?1")
    int countByCreatedAt(LocalDate date);

    @EntityGraph(attributePaths = {"penalty"})
    List<OffenseEntity> findByUserIdAndIdNot(Long userId, Long excludeId);

    boolean existsByExternalIdAndExternalServiceId(Long externalId, Long externalServiceId);

    Page<OffenseEntity> findByJudgeId(Long judgeId, Pageable pageable);

    Optional<OffenseEntity> findByIdAndJudgeId(Long id, Long judgeId);


    @Query("SELECT COUNT(o.id) > 0 FROM OffenseEntity o WHERE o.id = ?1 AND o.status = 'PROCESSING_AI_DECISION'")
    boolean existsByIdAndProcessing(Long id);


    @Modifying
    @Query("update OffenseEntity o set o.status = 'PROCESSING_AI_DECISION', o.updatedAt = CURRENT_TIMESTAMP WHERE o.id = ?1 AND o.judge.id = ?2 AND (o.status = 'PENDING' OR o.status = 'DRAFT_PENALTY')")
    int markForAiDecisionProcessing(Long id, Long judgeId);


    @Modifying
    @Query("update OffenseEntity o set o.status = :status, o.updatedAt = CURRENT_TIMESTAMP WHERE o.id = :offenseId")
    void updateStatus(@Param("offenseId") Long offenseId, @Param("status") OffenseStatus status);

}
