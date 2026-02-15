package com.uzumtech.court.repository;

import com.uzumtech.court.entity.OffenseEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface OffenseRepository extends JpaRepository<OffenseEntity, Long> {

    @Query("SELECT COUNT(e) FROM OffenseEntity e WHERE DATE(e.createdAt) = ?1")
    int countByCreatedAt(LocalDate date);

    @EntityGraph(attributePaths = {"penalty"})
    List<OffenseEntity> findByUserIdAndIdNot(Long userId, Long excludeId);

    boolean existsByExternalId(Long externalId);
}
