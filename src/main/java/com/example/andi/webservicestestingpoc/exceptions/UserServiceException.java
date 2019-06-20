package com.example.andi.webservicestestingpoc.exceptions;


public class UserServiceException extends RuntimeException {

    private static final long serialVersionUID = 672463267329421199L;

    public UserServiceException(String message){
        super(message);
    }
}
