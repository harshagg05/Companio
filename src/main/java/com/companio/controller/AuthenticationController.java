package com.companio.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.companio.service.VerifyByTokenService;

@RestController
public class AuthenticationController {
    
    @Autowired
    VerifyByTokenService verifyByTokenService;

    @GetExchange("/verify-by-token")
    public ResponseEntity<String> verifyByToken(@RequestParam String token){
        return verifyByTokenService.verifyByToken(token);
    }
}
