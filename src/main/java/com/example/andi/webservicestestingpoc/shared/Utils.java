package com.example.andi.webservicestestingpoc.shared;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Utils {

    public String generateUUID(){
        String uUID =  UUID.randomUUID().toString();
        return uUID.substring(0, 30);
    }

}
