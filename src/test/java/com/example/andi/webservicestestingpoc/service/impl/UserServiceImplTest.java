package com.example.andi.webservicestestingpoc.service.impl;

import com.example.andi.webservicestestingpoc.io.entity.UserEntity;
import com.example.andi.webservicestestingpoc.io.repository.UserRepository;
import com.example.andi.webservicestestingpoc.shared.Utils;
import com.example.andi.webservicestestingpoc.shared.dto.AddressDto;
import com.example.andi.webservicestestingpoc.shared.dto.UserDto;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Utils utils;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    String userId = "hhtwer234";
    String encryptedPassword = "öaskld09809";

    UserEntity userEntity;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("test@test.com");
        userEntity.setFirstName("Andi");
        userEntity.setUserId(userId);
        userEntity.setEncryptedPassword(encryptedPassword);
    }

    @org.junit.jupiter.api.Test
    final void getUser() {

        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDto userDto = userService.getUser("test@test.com");

        assertNotNull(userDto);
        assertEquals("Andi", userDto.getFirstName());
    }

    @Test
    final void testGetUser_UsernameNotFoundException(){
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                () -> {
                    userService.getUser("test@test.com");
                });
    }

    @Test
    final void testCreateUser(){

        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(utils.generateUUID()).thenReturn("abcd1234");
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        when(userRepository.save(ArgumentMatchers.any(UserEntity.class))).thenReturn(userEntity);

        AddressDto addressDto = new AddressDto();
        addressDto.setType("shipping");

        List<AddressDto> addresses = new ArrayList<>();
        addresses.add(addressDto);

        UserDto userDto = new UserDto();
        userDto.setAddresses(addresses);

        userService.createUser(userDto);

        UserDto storedUserDetails = userService.createUser(userDto);

        assertNotNull(storedUserDetails);
        assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
    }

}