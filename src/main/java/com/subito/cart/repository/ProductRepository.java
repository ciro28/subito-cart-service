package com.subito.cart.repository;

import com.subito.cart.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findByNameAndDeletedIsFalse(String name);

    List<ProductEntity> findByIdInAndDeletedIsFalse(List<Long> ids);

    Optional<ProductEntity> findByIdAndDeletedIsFalse(Long id);

    Page<ProductEntity> findByDeletedIsFalse(Pageable pageable);
}