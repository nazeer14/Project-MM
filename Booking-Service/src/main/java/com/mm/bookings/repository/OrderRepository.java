package com.mm.bookings.repository;

import com.mm.bookings.entity.Order;
import com.mm.bookings.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderId(String orderId);

    @Query("""
            SELECT o FROM Order o
            WHERE (:customerId IS NULL OR o.customerId = :customerId)
            AND (:farmId IS NULL OR o.farmId = :farmId)
            AND (:status IS NULL OR o.status = :status)
           """)
    Page<Order> findAllByFilters(
            Long customerId,
            Long farmId,
            OrderStatus status,
            Pageable pageable
    );
}
