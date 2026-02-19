package com.uzumtech.court.repository;

import com.uzumtech.court.entity.JudgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JudgeRepository extends JpaRepository<JudgeEntity, Long> {

    Optional<JudgeEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT j FROM JudgeEntity j WHERE j.isActive = true ORDER BY RANDOM() LIMIT 1")
    Optional<JudgeEntity> findRandomJudge();
}
