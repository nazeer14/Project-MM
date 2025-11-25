package com.mm.bookings.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateOrderRequest {

    @NotNull
    private Long customerId;

    @NotNull
    private Long farmId;

    @NotNull
    private String customerAddress;

    @NotNull
    @Positive
    private Double amount;

    // Optional: initial status (if null, service sets PENDING)
    private String status;        // e.g. PENDING, CONFIRMED

    // Optional: initial payment details
    private String paymentMode;   // COD, UPI, ONLINE
    private String paymentStatus; // PENDING, PAID, FAILED
}
