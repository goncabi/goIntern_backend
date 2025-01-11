package com.example.application.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Repräsentiert eine Benutzerin, die die Anwendung als Studentin verwendet.
 * <p> Diese Klasse ist eine JPA-Entität, die in der Datenbank in der Tabelle "studentin" gespeichert wird. Sie enthält Informationen über die Matrikelnummer, das Passwort und die AppUserRole. </p>
 */
@Getter @Setter
@Data
@Entity
@Table(name = "studentin")
public class Studentin {
    /**
     * Die eindeutige Matrikelnummer der Studentin. Sie muss einzigartig und darf nicht null sein, um in der Datenbank gespeichert zu werden.
     */
    @Id
    @Column(name = "matrikelnummer", unique = true, nullable = false)
    private String matrikelnummer;
    /**
     * Das Passwort der Studentin.
     */
    private String password;
    /**
     * Die Rolle, die die Studentin einnimmt bei der Benutzung der Anwendung.
     */
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;

    /**
     * Standardkonstruktor für die JPA.
     */
    public Studentin() {}

    /**
     * Konstruktor für die Erstellung einer neuen Studentin.
     *
     * @param matrikelnummer
     * @param password
     * @param appUserRole
     */
    public Studentin(String matrikelnummer, String password, AppUserRole appUserRole) {
        this.matrikelnummer = matrikelnummer;
        this.password = password;
        this.appUserRole = appUserRole;
    }
}

