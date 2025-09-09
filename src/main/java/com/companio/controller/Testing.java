package com.companio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.companio.service.JwtService;

@RestController
public class Testing {

    @Autowired
    JwtService jwtService;

    @ResponseBody
    @GetMapping("/first")
    public String test(){
        return "This is First Testing Controller";
    }
    @GetMapping("/key")
    public String key(){
        System.out.println("Key Method From testing got calles");
        return "ok Done";
    }
}
