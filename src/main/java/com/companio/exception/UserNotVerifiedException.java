package com.companio.exception;

import org.springframework.security.core.AuthenticationException;

public class UserNotVerifiedException extends AuthenticationException{
    public UserNotVerifiedException(String message){
        super(message);
    }
}
