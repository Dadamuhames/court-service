package com.uzumtech.court.repository;

import com.uzumtech.court.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByPinfl(String pinfl);

}
