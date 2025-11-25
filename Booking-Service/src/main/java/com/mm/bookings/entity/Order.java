package com.mm.bookings.entity;

import java.time.LocalDateTime;

public class Order {

    private Long id;

    private Long customerId;

    private Long farmId;

    private LocalDateTime createdAt;

    private String status;

    private LocalDateTime updatedAt;

    private int version;
}
