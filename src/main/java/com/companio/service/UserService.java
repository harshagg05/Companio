package com.companio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.companio.model.User;
import com.companio.repo.UserRepo;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    VerifyByTokenService verifyByTokenService;

    public ResponseEntity<String> verify(User user) {
        System.out.println("Verify Method Called in User Service");
        try{
            Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

            if(authentication.isAuthenticated()){
                // return ResponseEntity.ok("User is Valid and have its email: !"+user.getEmail());
                System.out.println("User That is autehnticated is: "+user.getEmail());
                return ResponseEntity.ok("User is valid and generated token is : "+jwtService.generateToken(user.getEmail()));
            }
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage()+" Inside User Service");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication Failed from UserService");
    }

    public ResponseEntity<String> add(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        String link = verifyByTokenService.createAndSentVerifyToken(user.getEmail());
        System.out.println(link);
        return ResponseEntity.ok("User Added Successfully and link is sent in email");
    }


}
