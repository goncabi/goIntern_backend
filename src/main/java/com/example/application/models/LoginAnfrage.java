package com.example.application.models;

/**
 * LoginAnfrage repräsentiert die Daten, die ein Benutzer während der Anmeldung bereitstellt.
 * <p>
 * Diese Klasse enthält die Rolle, den Benutzernamen und das Passwort,
 * die für die Authentifizierung erforderlich sind.
 * </p>
 *
 * <ul>
 *   <li>Verwendet die {@code @Getter}-Annotation von Lombok, um automatisch Getter-Methoden zu generieren.</li>
 *   <li>Bietet einen Konstruktor zur Initialisierung der Felder.</li>
 * </ul>
 *
 * @author Beyza Nur Acikgöz
 * @version 1.0
 */

import lombok.Getter;
@Getter
public class LoginAnfrage {
    /**
     * Die Rolle des Benutzers (z. B. "Student/in", "Administrator").
     */
    private String role;

    /**
     * Der Benutzername des Benutzers.
     */
    private String username;

    /**
     * Das Passwort des Benutzers.
     */
    private String password;

    /**
     * Konstruktor zur Initialisierung der Login-Daten.
     *
     * @param role     Die Rolle des Benutzers.
     * @param username Der Benutzername des Benutzers.
     * @param password Das Passwort des Benutzers.
     */
    public LoginAnfrage(String role, String username, String password) {
        this.role = role;
        this.username = username;
        this.password = password;
    }
}
