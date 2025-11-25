package com.mm.bookings.repository;

import com.mm.bookings.entity.Order;
import com.mm.bookings.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findByOrderId(String orderId);

    Page<Order> findAllByFilters(Long customerId, Long farmId, OrderStatus statusEnum, Pageable pageable);
}
