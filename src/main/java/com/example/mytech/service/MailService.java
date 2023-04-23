package com.example.mytech.service;

import org.springframework.stereotype.Service;

@Service
public interface MailService {

    public void sendEmail(String email, String subject, String text);
}
