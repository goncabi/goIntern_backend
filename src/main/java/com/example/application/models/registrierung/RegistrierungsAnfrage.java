package com.example.application.models.registrierung;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class RegistrierungsAnfrage {

    private final String matrikelnummer;
    private final String passwort;
    private final String antwort1;
    private final String antwort2;
    private final String antwort3;

    public RegistrierungsAnfrage(String matrikelnummer, String passwort, String antwort1, String antwort2, String antwort3) {
        this.matrikelnummer = matrikelnummer;
        this.passwort = passwort;
        this.antwort1 = antwort1;
        this.antwort2 = antwort2;
        this.antwort3 = antwort3;
    }
}
