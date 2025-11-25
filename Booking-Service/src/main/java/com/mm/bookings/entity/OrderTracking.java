package com.mm.bookings.entity;

import com.mm.bookings.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table
@Data
public class OrderTracking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,updatable = false)
    private String orderId;
    @Column(nullable = false,updatable = false)
    private Long customerId;
    @Column(nullable = false,updatable = false)
    private Long deliveryAgentId;

    @Column(nullable = false)
    private String deliveryAgentLocation;

    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private int version;
}
