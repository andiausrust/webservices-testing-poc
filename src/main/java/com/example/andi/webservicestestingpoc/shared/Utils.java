package com.example.andi.webservicestestingpoc.shared;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Utils {

    public String generateUUID(){
        return UUID.randomUUID().toString();
    }

}
