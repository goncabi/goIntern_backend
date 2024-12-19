package com.example.application.models;

import lombok.Getter;

@Getter
public class LoginAnfrage {
    private String role;
    private String username;
    private String password;

    public LoginAnfrage(String role, String username, String password) {
        this.role = role;
        this.username = username;
        this.password = password;
    }
}
