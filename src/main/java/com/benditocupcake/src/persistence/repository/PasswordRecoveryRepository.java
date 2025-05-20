package com.benditocupcake.src.persistence.repository;

import com.benditocupcake.src.persistence.entity.PasswordRecoveryEntity;
import com.benditocupcake.src.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordRecoveryRepository extends JpaRepository<PasswordRecoveryEntity, Long> {
    Optional<PasswordRecoveryEntity> findByCode(String code);
    Optional<PasswordRecoveryEntity> findByUser(UserEntity user);
}