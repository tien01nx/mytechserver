package com.example.mytech.service.impl;

import com.example.mytech.repository.UserRepository;
import com.example.mytech.service.ForgotPassWordService;
import com.example.mytech.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ForgotPassWordServiceImpl implements ForgotPassWordService {

    @Override
    public String generateNewPassword() {
        return UUID.randomUUID().toString();

    }
}
