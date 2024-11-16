package com.example.application.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class RegistrierungsAnfrage {

    private final String matrikelnummer;
    private final String passwort1;
    private final String passwort2;
    private final int frage;
    private final String antwort;

    public RegistrierungsAnfrage(String matrikelnummer, String passwort1, String passwort2, int frage, String antwort) {
        this.matrikelnummer = matrikelnummer;
        this.passwort1 = passwort1;
        this.passwort2 = passwort2;
        this.frage = frage;
        this.antwort = antwort;
    }
}
