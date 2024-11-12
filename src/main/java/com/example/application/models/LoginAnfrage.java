package com.example.application.models;

import lombok.Getter;

@Getter
public class LoginAnfrage {
    private String matrikelnummer;
    private String passwort;

    public LoginAnfrage(String matrikelnummer, String passwort) {
        this.matrikelnummer = matrikelnummer;
        this.passwort = passwort;
    }
}
