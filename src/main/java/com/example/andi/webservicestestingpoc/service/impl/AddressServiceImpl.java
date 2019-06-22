package com.example.andi.webservicestestingpoc.service.impl;

import com.example.andi.webservicestestingpoc.io.entity.AddressEntity;
import com.example.andi.webservicestestingpoc.io.entity.UserEntity;
import com.example.andi.webservicestestingpoc.io.repository.AddressRepository;
import com.example.andi.webservicestestingpoc.io.repository.UserRepository;
import com.example.andi.webservicestestingpoc.service.AddressService;
import com.example.andi.webservicestestingpoc.shared.dto.AddressDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<AddressDto> getAddresses(String userId) {
        List<AddressDto> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) return returnValue;

        Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
        for (AddressEntity addressEntity : addresses){
            returnValue.add(modelMapper.map(addressEntity, AddressDto.class));
        }

        return null;
    }
}
