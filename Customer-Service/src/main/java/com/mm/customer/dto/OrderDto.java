package com.mm.customer.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long orderId;
    private Long farmId;
    private Long deliveryPartnerId;
    private String quantity;
    private LocalDateTime createdAt;
    private int price;
    private String paymentStatus;
    private LocalDateTime completedAt;
    private String status;
}
