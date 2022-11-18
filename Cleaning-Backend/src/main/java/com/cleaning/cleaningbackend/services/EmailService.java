package com.cleaning.cleaningbackend.services;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);
}
