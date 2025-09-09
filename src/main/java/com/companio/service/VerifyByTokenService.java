package com.companio.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.companio.model.User;
import com.companio.model.VerifyToken;
import com.companio.repo.UserRepo;
import com.companio.repo.VerifyTokenRepo;

@Service
public class VerifyByTokenService {

    @Autowired
    VerifyTokenRepo verifyTokenRepo;

    @Autowired
    UserRepo userRepo;

    public ResponseEntity<String> verifyByToken(String token){
        System.out.println("Verify-by-tokne is called that present inside VerifyByToken Service");
        Optional<VerifyToken> istoken = verifyTokenRepo.findByToken(token);
        if(istoken.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token or already Used");
        }

        VerifyToken verifyToken = istoken.get();

        if(verifyToken.getExpiryDate().isBefore(LocalDateTime.now())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token Expired");
        }

        User user = userRepo.findByEmail(verifyToken.getEmail()).orElseThrow(()-> new UsernameNotFoundException("User Not Found from VerifyByToken Service"));

        user.setVerified(true);
        userRepo.save(user);
        

        verifyTokenRepo.delete(verifyToken);

        return ResponseEntity.status(HttpStatus.OK).body("User Verified Successfully now to go home page and login");
    }

    public String createAndSentVerifyToken(String email) {
         String token = UUID.randomUUID().toString();
        LocalDateTime dateTime = LocalDateTime.now().plusHours(24);


        VerifyToken verifyToken = new VerifyToken();
        verifyToken.setEmail(email);
        verifyToken.setExpiryDate(dateTime);
        verifyToken.setToken(token);
        verifyTokenRepo.save(verifyToken);
        return "http://localhost:9090/verify-by-token?token="+token;
    }


}
