package com.example.mytech.service;

import com.example.mytech.model.request.ChangePassWordRep;
import org.springframework.stereotype.Service;

@Service
public interface ChangePassWordService {

    public void changePassWord (String email , ChangePassWordRep rep) throws Exception;
}
