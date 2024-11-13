package com.example.application.controller;

import com.example.application.models.LoginAnfrage;
import com.example.application.services.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestBody LoginAnfrage loginAnfrage){
        if(loginService.login(loginAnfrage)){
            return "Login OK";
            //Hier dann weiterleiten zu der Homepage
        }
        return "Login Failed: Password is incorrect";
    }
}
