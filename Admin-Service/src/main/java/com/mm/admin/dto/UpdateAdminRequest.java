package com.mm.admin.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateAdminRequest {

    private String name;

    @Size(max = 15)
    private String number;

    private String officeLocation;
    private String location;
    private String address;

    private Boolean verified;
    private Boolean locked;
}
