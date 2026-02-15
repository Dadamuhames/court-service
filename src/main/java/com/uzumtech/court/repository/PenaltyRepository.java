package com.uzumtech.court.repository;

import com.uzumtech.court.constant.enums.PenaltyStatus;
import com.uzumtech.court.entity.PenaltyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface PenaltyRepository extends JpaRepository<PenaltyEntity, Long> {

    @Modifying
    @Query("update PenaltyEntity p SET p.status = 'SENDING' WHERE p.id = :penaltyId AND p.status = 'NEW'")
    int claimForProcessing(Long penaltyId);

    @Modifying
    @Query("update PenaltyEntity p SET p.status = 'NEW' WHERE p.id = :penaltyId AND p.status = 'SENDING'")
    int unclaim(Long penaltyId);


    @Modifying
    @Query("update PenaltyEntity p set p.status = :status, p.updatedAt = CURRENT_TIMESTAMP WHERE p.id = :penaltyId ")
    void updateStatus(@Param("requestId") Long penaltyId, @Param("status") PenaltyStatus status);

}
