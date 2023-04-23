package com.example.mytech.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "admin/authentication/login";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "admin/authentication/register";
    }
}
