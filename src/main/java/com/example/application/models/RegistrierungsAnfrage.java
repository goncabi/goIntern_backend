package com.example.application.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Repräsentiert eine Registrierungsanfrage mit den benötigten Benutzerdaten.
 * <p>
 * Diese Klasse wird verwendet, um die Registrierungsinformationen eines neuen Benutzers
 * (Studentin) zu kapseln, einschließlich Benutzername, Passwörter, Sicherheitsfrage-ID und Antwort.
 * </p>
 * <p>
 * Die Klasse ist unveränderlich, da alle Felder als final deklariert sind,
 * und sie stellt Getter-Methoden über die Lombok-Annotation {@code @Getter} bereit.
 * </p>
 *
 * @author Noa Sauter
 */
@Getter
@EqualsAndHashCode
@ToString
public class RegistrierungsAnfrage {
    /**
     * Der Benutzername (Matrikelnummer) des neuen Benutzers.
     */
    private final String username;
    /**
     * Das vom Benutzer gewählte Passwort.
     */
    private final String password;
    /**
     * Die Bestätigung des vom Benutzer gewählten Passworts.
     */
    private final String passwordConfirm;
    /**
     * Die ID der Sicherheitsfrage, die vom Benutzer ausgewählt wurde.
     */
    private final String frageId;
    /**
     * Die Antwort auf die ausgewählte Sicherheitsfrage.
     */
    private final String answer;

    /**
     * Konstruktor zur Erstellung einer neuen Registrierungsanfrage.
     *
     * @param matrikelnummer Der Benutzername (Matrikelnummer) des neuen Benutzers.
     * @param passwort1      Das vom Benutzer gewählte Passwort.
     * @param passwort2      Die Bestätigung des Passworts.
     * @param frage          Die ID der ausgewählten Sicherheitsfrage.
     * @param antwort        Die Antwort auf die Sicherheitsfrage.
     */
    public RegistrierungsAnfrage(String matrikelnummer, String passwort1, String passwort2, String frage, String antwort) {
        this.username = matrikelnummer;
        this.password = passwort1;
        this.passwordConfirm = passwort2;
        this.frageId = frage;
        this.answer = antwort;
    }
}
