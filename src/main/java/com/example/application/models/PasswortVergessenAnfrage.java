package com.example.application.models;

import lombok.Getter;

@Getter
public class PasswortVergessenAnfrage {
    private final String matrikelnummer;
    private final Sicherheitsantwort sicherheitsantwort;

    public PasswortVergessenAnfrage(String matrikelnummer, int frage, String answer) {
        this.matrikelnummer = matrikelnummer;
        this.sicherheitsantwort = new Sicherheitsantwort(frage, matrikelnummer, answer);
    }
}
