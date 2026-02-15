package com.uzumtech.court.repository;

import com.uzumtech.court.entity.BhmAmountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BhmAmountRepository extends JpaRepository<BhmAmountEntity, Long> {

    @Query("SELECT b FROM BhmAmountEntity b WHERE b.isActive = true ORDER BY id DESC LIMIT 1")
    Optional<BhmAmountEntity> findCurrentAmount();
}
