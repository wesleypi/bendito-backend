package com.benditocupcake.src.persistence.repository;

import com.benditocupcake.src.persistence.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM ProductEntity p WHERE (:category IS NULL OR p.category = :category)")
    Page<ProductEntity> findAllByCategory(@Param("category") String category, Pageable pageable);

    boolean existsByName(String name);
}

