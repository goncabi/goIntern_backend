package com.example.application.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

/**
 * Die Klasse speichert die Sicherheitsfrage und die Antwort eines Benutzers.
 *
 * <p>
 *     Sie enthält Informationen wie die ID der Sicherheitsantwort, die Frage,
 *     die Matrikelnummer des Benutzers und die Antwort.
 * </p>
 */


@Data
@Entity
@Table
@Getter
public class Sicherheitsantwort {

    /**
     * Die eindeutige ID der Sicherheitsantwort.
     */

    @Id
    @GeneratedValue
    private Long antwortID;

    /**
     * Die Sicherheitsfrage
     */
    private int frage;

    /**
     * Die Matrikelnummer des Benutzers, der die Antwort gibt.
     */
    private String matrikelnummer;

    /**
     * Die Antwort des Benutzers auf die Sicherheitsfrage.
     */
    private String antwort;

    /**
     * Konstruktor, um eine Sicherheitsantwort mit alles Daten zu erstellen.
     * @param frage Die Sicherheitsfrage
     * @param matrikelnummer Die Matrikelnummer des Benutzers.
     * @param antwort Die Antwort des Benutzers.
     */

    public Sicherheitsantwort(int frage, String matrikelnummer, String antwort) {
        this.frage = frage;
        this.matrikelnummer = matrikelnummer;
        this.antwort = antwort;
    }

    public Sicherheitsantwort() {
    }
}
