package com.mm.admin.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username; // employeeId or number as I prefer
    private String password;
}
