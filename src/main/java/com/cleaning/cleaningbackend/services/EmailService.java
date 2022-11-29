package com.cleaning.cleaningbackend.services;

public interface EmailService {


    void sendSimpleMail(String sender,String to,String subject,String text);
}
