package com.companio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.companio.controller.UserPrinciple;
import com.companio.exception.UserEmailNotFoundException;
import com.companio.exception.UserNotVerifiedException;
import com.companio.model.User;
import com.companio.repo.UserRepo;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Load By Username Method got called");
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UserEmailNotFoundException("Please Enter a Valid Email: " + username));

        if (!user.isVerified()) {
            System.out.println("Sending User Not Verified Exception ");
            throw new UserNotVerifiedException("User is not Verified. Please Verify First!");
        }

        return new UserPrinciple(user);
    }

}
