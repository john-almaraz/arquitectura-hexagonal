package com.almaraz_john.user_service.infrastructure.adapters.output.persistence.repository;

import com.almaraz_john.user_service.infrastructure.adapters.output.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryJPA extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findUserByEmail(String email);
}
