package com.example.application.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class RegistrierungsAnfrage {

    private final String username;
    private final String password;
    private final String passwordConfirm;
    private final String frageId;
    private final String answer;

    public RegistrierungsAnfrage(String matrikelnummer, String passwort1, String passwort2, String frage, String antwort) {
        this.username = matrikelnummer;
        this.password = passwort1;
        this.passwordConfirm = passwort2;
        this.frageId = frage;
        this.answer = antwort;
    }
}
