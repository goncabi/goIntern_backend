package com.example.application.models;

import lombok.Getter;

@Getter
public class LoginAnfrageStudentin {

    private String matrikelnummer;
    private String passwort;

    public LoginAnfrageStudentin(String matrikelnummer, String passwort) {
        this.matrikelnummer = matrikelnummer;
        this.passwort = passwort;
    }
}
