package com.companio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.companio.service.JwtService;

@RestController
@RequestMapping("v1/api/test")
public class Testing {

    @Autowired
    JwtService jwtService;

    @GetMapping
    public String test(){
        return "This is First Testing Controller";
    }
}
