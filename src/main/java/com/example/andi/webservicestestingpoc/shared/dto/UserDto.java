package com.example.andi.webservicestestingpoc.shared.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = 62877493452230156L;

    private long id;        // auto generated id from database
    private String userId;  // shared id which could be passed around
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private String emailVerificationToken;
    private Boolean emailVerificationStatus = false;

}
