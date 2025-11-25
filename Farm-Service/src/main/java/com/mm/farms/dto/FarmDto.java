package com.mm.farms.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FarmDto {

    private String name;

    private String contactNumber;

    private String areaZone;

    private String address;

    private String lat;

    private String lon;

    @Column(length = 50)
    private String area;

    private Integer noOfUnits;
}
