package com.companio.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UserDetailService.class);

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("LoadByUsername Function Called");
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UserEmailNotFoundException("Please Enter a Valid Email: " + username));

        if (!user.isVerified()) {
            logger.error("User is Not Verified Checked in LoadByUserName Function Sending Exception UserNotVerficationException");
            throw new UserNotVerifiedException("User is not Verified. Please Verify First!");
        }
        logger.info("LoadByUserName SuccessFull");
        return new UserPrinciple(user);
    }

}
