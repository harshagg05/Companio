package com.companio.service;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendSuccessfullCreationMail(String toMail){//Used in Verify by Token Service
        SimpleMailMessage message = new SimpleMailMessage();
        String subject = "Welcome to my company";
        String body = "Hi " + toMail + ",\n\n"
                    + "Welcome to [Your Company Name]! Your account has been created successfully. We’re excited to have you on board.\n"
                    + "Start exploring! \n"
                    +"Thanks\n";

        message.setTo(toMail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        logger.info("Welcome Mail has been send to user");
    }

    @Async
    public void sendAccountCreationMailAfterVerification(String toMail){
        SimpleMailMessage message = new SimpleMailMessage();
        String subject = "Congratulations on Creating Your Account";
        String body = "Congratulations on successfully creating your account with us! You have completed the first important step.\n\n"
        +"Now, there is only one last step - please verify your account by clicking the verification link sent to your email.\n"
        +"If you did not receive the verification mail, please check your spam folder or request a new verification link."
        +"Thank you for joing us!";
        message.setTo(toMail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        logger.info("Verification mail has been send to user ");
    }

    @Async
    public void sendVerficationMail(String toMail,String tokenLink){
        SimpleMailMessage message = new SimpleMailMessage();
        String subject = "Verify Your Account to Get Started";
        String body = "Congratulations on successfully your account with us!\n\n"
        + "To complete your registration, please verify your account by clicking the link below: \n\n"
        + tokenLink + "\n\n"
        + "This link is valid for a Limited time."
        + "If your did not sign up for this account, you can safely ignore this email.\n\n";

        message.setTo(toMail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        logger.info("Account Creation mail has been send to user");
    }
}