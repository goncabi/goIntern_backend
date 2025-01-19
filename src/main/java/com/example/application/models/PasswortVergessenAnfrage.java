package com.example.application.models;

/**
 * Diese Klasse speichert die Daten für das Zurücksetzten eines Passworts.
 *
 * Sie enthält die Matrikelnumer, die Antwort auf die Sicherheitsfrage und das neue Passwort des Nutzers.
 */

import lombok.Getter;

@Getter
public class PasswortVergessenAnfrage {
    /** Matrikelnummer des Nutzers*/
    private String matrikelnummer;
    /** Antwort auf die Sicherheitsfrage*/
    private String antwort;
    /** Neues Passwort des Nutzers*/
    private String passwort;

    /**
     * Erstellt eine neue Anfrage zum Zurücksetzten des Passworts.
     *
     * @param matrikelnummer Die Matrikelnummer des Nutzers.
     * @param antwort Die antwort auf die Sicherheitsfrage.
     * @param passwort das neue Passwort.
     */

    public PasswortVergessenAnfrage(String matrikelnummer, String antwort, String passwort) {
        this.matrikelnummer = matrikelnummer;
        this.antwort = antwort;
        this.passwort = passwort;
    }
}
