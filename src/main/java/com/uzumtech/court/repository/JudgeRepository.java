package com.uzumtech.court.repository;

import com.uzumtech.court.entity.JudgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JudgeRepository extends JpaRepository<JudgeEntity, Long> {

    Optional<JudgeEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
