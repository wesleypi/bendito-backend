package com.benditocupcake.src.persistence.repository;

import com.benditocupcake.src.persistence.entity.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByTaxId(String taxId);
}