package com.mm.bookings.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateOrderStatusRequest {

    @NotBlank
    private String status; // e.g. CONFIRMED, CANCELLED, DELIVERED
}

