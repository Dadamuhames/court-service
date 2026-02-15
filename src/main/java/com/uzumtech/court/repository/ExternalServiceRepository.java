package com.uzumtech.court.repository;

import com.uzumtech.court.entity.ExternalServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExternalServiceRepository extends JpaRepository<ExternalServiceEntity, Long> {


    Optional<ExternalServiceEntity> findByLogin(String login);

    boolean existsByLogin(String login);
}
