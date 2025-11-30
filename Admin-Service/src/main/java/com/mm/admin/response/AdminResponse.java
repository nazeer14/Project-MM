package com.mm.admin.response;

import lombok.Data;

@Data
public class AdminResponse {

    private Long id;
    private String employeeId;
    private String name;
    private String number;
    private String officeLocation;
    private String location;
    private String address;
    private Boolean verified;
    private Boolean locked;
}
