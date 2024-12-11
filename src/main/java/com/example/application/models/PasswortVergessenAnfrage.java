package com.example.application.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswortVergessenAnfrage {
    private String matrikelnummer;
    private int frageID;
    private Sicherheitsantwort sicherheitsantwort;

    public PasswortVergessenAnfrage(){}

    public PasswortVergessenAnfrage(String matrikelnummer, int frage, String answer) {
        this.matrikelnummer = matrikelnummer;
        this.sicherheitsantwort = new Sicherheitsantwort(frage, matrikelnummer, answer);
    }
}
