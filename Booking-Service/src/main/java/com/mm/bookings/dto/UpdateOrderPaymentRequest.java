package com.mm.bookings.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateOrderPaymentRequest {

    @NotBlank
    private String paymentMode;   // COD, UPI, ONLINE

    @NotBlank
    private String paymentStatus; // PENDING, PAID, FAILED
}
