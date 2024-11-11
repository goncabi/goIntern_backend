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
    private final String antwort1;
    private final String antwort2;
    private final String antwort3;

    public RegistrierungsAnfrage(String matrikelnummer, String passwort1, String passwort2, String antwort1, String antwort2, String antwort3) {
        this.matrikelnummer = matrikelnummer;
        this.passwort1 = passwort1;
        this.passwort2 = passwort2;
        this.antwort1 = antwort1;
        this.antwort2 = antwort2;
        this.antwort3 = antwort3;
    }
}
