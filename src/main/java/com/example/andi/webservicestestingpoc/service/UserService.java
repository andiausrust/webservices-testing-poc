package com.example.andi.webservicestestingpoc.service;

import com.example.andi.webservicestestingpoc.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto user);
    UserDto getUser(String email);

}
