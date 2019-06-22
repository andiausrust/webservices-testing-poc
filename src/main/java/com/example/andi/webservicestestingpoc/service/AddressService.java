package com.example.andi.webservicestestingpoc.service;

import com.example.andi.webservicestestingpoc.shared.dto.AddressDto;

import java.util.List;

public interface AddressService {

    List<AddressDto> getAddresses(String userId);

}
