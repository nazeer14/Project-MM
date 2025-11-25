package com.mm.customer.dto;

import lombok.*;


import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto implements Serializable {


    private String name;

    private String contactNumber;

    private String location;

    private String address;

    private Boolean isVerified;


}
