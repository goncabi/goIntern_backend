package com.example.application.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Sicherheitsfrage repräsentiert eine Entität für Sicherheitsfragen,
 * die für die Benutzerverifikation verwendet werden können.
 * <p>
 * Diese Klasse ist mit JPA-Annotationen versehen, um die Speicherung und
 * Verwaltung in einer relationalen Datenbank zu ermöglichen.
 * </p>
 *
 * <ul>
 *   <li>Verwendet die Annotation {@code @Entity}, um eine Datenbank-Entität zu definieren.</li>
 *   <li>Die Tabelle, in der die Entität gespeichert wird, ist durch {@code @Table} gekennzeichnet.</li>
 *   <li>Verwendet Lombok {@code @Data}, um automatisch Getter, Setter, equals, hashCode und toString zu generieren.</li>
 * </ul>
 *
 * @author Beyza Nur Acikgöz
 * @version 1.0
 */

@Data
@Entity
@Table
public class Sicherheitsfrage {
    /**
     * Die eindeutige ID der Sicherheitsfrage.
     * <p>
     * Dieses Feld wird als Primärschlüssel verwendet und mit {@code @Id} gekennzeichnet.
     * </p>
     */
    @Id
    private long frageID;
    /**
     * Der Text der Sicherheitsfrage.
     * <p>
     * Beispiel: "Wie lautet der Name Ihres ersten Haustiers?"
     * </p>
     */
    private String frage;
    /**
     * Konstruktor zur Initialisierung einer neuen Sicherheitsfrage mit spezifischen Werten.
     *
     * @param frageID Die eindeutige ID der Frage.
     * @param frage   Der Text der Sicherheitsfrage.
     */
    public Sicherheitsfrage(int frageID, String frage) {
        this.frageID = frageID;
        this.frage = frage;
    }
    /**
     * Standard-Konstruktor für Sicherheitsfrage.
     * <p>
     * Wird benötigt, um eine leere Instanz der Klasse zu erstellen,
     * z. B. bei der Verwendung von JPA.
     * </p>
     */
    public Sicherheitsfrage() {

    }
}
