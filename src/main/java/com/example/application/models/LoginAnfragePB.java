package com.example.application.models;

import lombok.Getter;

@Getter

public class LoginAnfragePB {

    private String username;
    private String passwort;

    public LoginAnfragePB(String username, String passwort) {
        this.username = username;
        this.passwort = passwort;
    }
}
