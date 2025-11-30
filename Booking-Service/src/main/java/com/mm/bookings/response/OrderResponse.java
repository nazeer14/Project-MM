package com.mm.bookings.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponse {

    private Long id;
    private String orderId;

    private Long customerId;
    private Long farmId;

    private String customerAddress;

    private String status;

    private Double amount;

    private String paymentMode;

    private String paymentStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private int version;
}
