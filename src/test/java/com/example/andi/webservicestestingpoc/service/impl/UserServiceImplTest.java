package com.example.andi.webservicestestingpoc.service.impl;

import com.example.andi.webservicestestingpoc.io.entity.UserEntity;
import com.example.andi.webservicestestingpoc.io.repository.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @org.junit.jupiter.api.Test
    void getUser() {

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("Andi");
        userEntity.setUserId("hhhtpp");
        userEntity.setEncryptedPassword("öaskdfjfjowie");

        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
    }
}