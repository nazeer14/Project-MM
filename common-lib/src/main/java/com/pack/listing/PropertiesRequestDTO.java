package com.pack.listing;

import lombok.Data;

import java.util.List;

@Data
public class PropertiesRequestDTO {

    private Long id;

    private Long ownerId;

    private String propertyType;

    private String address;

    private String lan;

    private String lng;

    private String propertyDetails;

    private Double price;

    private List<String> images;

    private String drawbacks;

    private String status;

    private String showType;
}
