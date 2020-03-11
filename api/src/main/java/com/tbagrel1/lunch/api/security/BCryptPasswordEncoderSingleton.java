package com.tbagrel1.lunch.api.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// TODO: improve
public class BCryptPasswordEncoderSingleton {
    private static BCryptPasswordEncoderSingleton instance = null;
    public static BCryptPasswordEncoderSingleton getInstance() {
        if (instance == null) {
            instance = new BCryptPasswordEncoderSingleton();
        }
        return instance;
    }

    private BCryptPasswordEncoderSingleton() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public BCryptPasswordEncoder getInner() {
        return bCryptPasswordEncoder;
    }
}
