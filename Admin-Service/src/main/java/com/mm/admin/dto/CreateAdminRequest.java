package com.mm.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAdminRequest {

    @NotBlank
    @Size(max = 30)
    private String employeeId;

    @NotBlank
    private String name;

    @NotBlank
    @Size(max = 15)
    private String number;

    @NotBlank
    private String password;

    private String officeLocation;
    private String location;
    private String address;
}
