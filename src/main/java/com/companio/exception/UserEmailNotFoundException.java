package com.companio.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserEmailNotFoundException extends UsernameNotFoundException{
    public UserEmailNotFoundException(String message){
        super(message);
    }
}
