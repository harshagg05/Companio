package com.companio.controller;
import com.companio.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.companio.model.User;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<String> add(@RequestBody User user){
        return userService.add(user);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody User user){
        return userService.verify(user);
    }
}
