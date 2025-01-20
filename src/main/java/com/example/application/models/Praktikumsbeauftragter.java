package com.example.application.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Repräsentiert einen Praktikumsbeauftragten in der Anwendung.
 * Diese Klasse speichert die grundlegenden Informationen eines Praktikumsbeauftragten,
 * einschließlich Benutzernamen, Passwort und der zugewiesenen Rolle.
 * Sie ist als JPA-Entität definiert und wird in der Tabelle "praktikumsbeauftragter" gespeichert.
 */
@Getter
@Setter
@Data
@Entity
@Table(name = "praktikumsbeauftragter")

public class Praktikumsbeauftragter {

    /**
     * Der Benutzername des Praktikumsbeauftragten
     */
    @Id
    private String username;

    /**
     * Das Passwort des Praktikumsbeauftragten zur Authentifizierung.
     */
    private String passwort;

    /**
     * Die Rolle des Praktikumsbeauftragten, die durch das Enum definiert wird.
     */
    @Enumerated(EnumType.STRING)
    private AppUserRole userRole;

    /**
     * Standardkonstruktor für die JPA.
     */
    public Praktikumsbeauftragter() {}

    /**
     * Konstruktor zum Erstellen eines Praktikumsbeauftragten.
     *
     * @param username der Benutzername des Praktikumsbeauftragten
     * @param passwort das Passwort des Praktikumsbeauftragten
     * @param appUserRole die Rolle des Praktikumsbeauftragten
     */
    public Praktikumsbeauftragter(String username, String passwort, AppUserRole appUserRole) {
            this.username = username;
            this.passwort = passwort;
            this.userRole = appUserRole;
        }
}
