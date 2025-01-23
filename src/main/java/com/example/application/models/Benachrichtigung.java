package com.example.application.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Repräsentiert eine Benachrichtigung, die an einen bestimmten Empfänger gesendet wird.
 * <p> Diese Klasse ist eine JPA-Entität, die in der Datenbank in der Tabelle "benachrichtigung" gespeichert wird. Sie enthält Informationen über die Nachricht, das Datum und den Empfänger. </p>
 *
 * @author Noa Sauter
 */
@Data
@Entity
@Table(name = "benachrichtigung")
@Getter
public class Benachrichtigung {

    /**
     * Die eindeutige ID der Benachrichtigung. Wird automatisch generiert.
     */
    @Id
    @GeneratedValue
    private Long nachrichtId;
    /**
     * Der Textinhalt der Benachrichtigung.
     */
    private String nachricht;
    /**
     * Das Datum und die Uhrzeit, zu der die Benachrichtigung erstellt wurde. Das Format ist "dd.MM.yyyy HH:mm".
     */
    private String datum;
    /**
     * Der Empfänger der Benachrichtigung.
     */
    private String empfaenger;
    /**
     * Wichtigkeit der Benachrichtigung - differenziert, welche automatisch gelöscht werden sollen und welche nicht.
     */
    @Setter
    @Enumerated(EnumType.STRING)
    private BenachrichtigungWichtigkeit wichtigkeit;

    /**
     * Standardkonstruktor für die JPA.
     */
    public Benachrichtigung() {}

    /**
     * Konstruktor zur Erstellung einer neuen Benachrichtigung.
     * <p>
     * Der Konstruktor formatiert das übergebene Datum automatisch in das Format "dd.MM.yyyy HH:mm".
     * </p>
     *
     * @param nachricht     Der Textinhalt der Benachrichtigung.
     * @param datum         Das Datum und die Uhrzeit der Benachrichtigung als {@link Date}-Objekt.
     * @param empfaenger_in Der Empfänger der Benachrichtigung.
     */
    public Benachrichtigung(String nachricht, Date datum, String empfaenger_in) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String formattedDate = sdf.format(datum);
        this.nachricht = nachricht;
        this.datum = formattedDate;
        this.empfaenger = empfaenger_in;
        this.wichtigkeit = BenachrichtigungWichtigkeit.UNWICHTIG;
    }

}
