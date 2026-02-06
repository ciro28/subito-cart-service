package com.subito.cart.repository;

import com.subito.cart.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("""
        SELECT o.id as orderId, o.totalPrice as totalPrice, o.totalVat as totalVat,
                   op.product.id as productId, op.price as price, op.vat as vat
        FROM OrderProductEntity op
        JOIN op.order o
        WHERE o.deleted IS FALSE
        ORDER BY o.id
    """)
    List<OrderProductProjection> findAllOrderProducts();

    Optional<OrderEntity> findByIdAndDeletedIsFalse(Long id);
}
