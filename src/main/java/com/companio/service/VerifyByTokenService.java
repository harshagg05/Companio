package com.companio.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(VerifyByTokenService.class);

    @Autowired
    VerifyTokenRepo verifyTokenRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    EmailService emailService;

    public ResponseEntity<String> verifyByToken(String token){
        // System.out.println("Verify-by-tokne is called that present inside VerifyByToken Service");
        logger.info("Verify by token method called");
        Optional<VerifyToken> istoken = verifyTokenRepo.findByToken(token);
        if(istoken.isEmpty()){
            logger.error("Invalid Token or already used");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token or already Used");
        }

        VerifyToken verifyToken = istoken.get();

        if(verifyToken.getExpiryDate().isBefore(LocalDateTime.now())){
            logger.error("Verify by token got experied");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token Expired");
        }

        User user = userRepo.findByEmail(verifyToken.getEmail()).orElseThrow(()-> new UsernameNotFoundException("User Not Found from VerifyByToken Service"));
        user.setVerified(true);
        userRepo.save(user);
        

        verifyTokenRepo.delete(verifyToken);
        //This will send the mail to user when the token got verified
        emailService.sendSuccessfullCreationMail(user.getEmail());

        logger.info("User got verified successfully by verify by token");
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
