package com.cleaning.cleaningbackend.services.impl;

import com.cleaning.cleaningbackend.exception.CleaningApiException;
import com.cleaning.cleaningbackend.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender  javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Override
    @Async
    public void sendSimpleMail(String sender, String to, String subject, String text) {


        // Try block to check for exceptions
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(this.sender);
            mailMessage.setTo(to);
            mailMessage.setText(text);
            mailMessage.setSubject(subject);

            // Sending the mail
            javaMailSender.send(mailMessage);
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            throw new CleaningApiException(HttpStatus.BAD_GATEWAY,"email service failed");
        }
    }
}