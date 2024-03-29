package com.example.andi.webservicestestingpoc.shared.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AddressDto {

    private long id;
    private String addressId;
    private String city;
    private String country;
    private String streetName;
    private String postalCode;
    private String type;
    private UserDto userDetails;
}