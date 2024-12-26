package com.example.application.models;

import lombok.Getter;

@Getter
public class PasswortVergessenAnfrage {
    private String matrikelnummer;
    private String antwort;
    private String passwort;

    public PasswortVergessenAnfrage(String matrikelnummer, String antwort, String passwort) {
        this.matrikelnummer = matrikelnummer;
        this.antwort = antwort;
        this.passwort = passwort;
    }
}
