package com.mm.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerResponse {

    private Long id;
    private String name;
    private String location;
    private String contactNumber;
    private Boolean verified;
    private Boolean locked;
}

