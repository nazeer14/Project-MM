package com.mm.admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SuperAdminRequest {
    @NotNull
    private String number;
    @NotNull
    private String password;
}
