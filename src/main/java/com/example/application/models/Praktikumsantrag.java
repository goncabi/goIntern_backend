package com.example.application.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
/**
 * Repräsentiert einen Praktikumsantrag mit den erforderlichen Daten.
 * <p>
 * Diese Klasse wird verwendet, um die Informationen zu einem Praktikumsantrag
 * zu kapseln, einschließlich der persönlichen Daten der Studentin/des Studenten,
 * Details zur Praktikumsstelle und den relevanten Statusinformationen.
 * </p>
 * <p>
 * Die Klasse ist veränderlich, da die Felder durch Getter- und Setter-Methoden
 * (bereitgestellt durch die Lombok-Annotationen {@code @Getter} und {@code @Setter})
 * geändert werden können. Sie wird als Entität in einer relationalen Datenbank gespeichert
 * und nutzt die JPA-Annotationen für die Definition der Datenbankstruktur.
 * </p>
 *
 * <h2>Verwendungszweck</h2>
 * <ul>
 *   <li>Kapselt alle Informationen zu einem Praktikumsantrag, die von Studierenden eingereicht werden.</li>
 *   <li>Wird in einer Datenbanktabelle `praktikumsantrag` gespeichert und für die Verwaltung von Anträgen genutzt.</li>
 *   <li>Ermöglicht die Nachverfolgung des Antragsstatus und der Details eines Praktikums, einschließlich Start- und Enddatum, Adresse der Praktikumsstelle und Ansprechpartner.</li>
 * </ul>
 *
 * <h2>Funktionalitäten</h2>
 * <ul>
 *   <li>Speichert persönliche Daten der Studierenden (z. B. Name, Matrikelnummer, Kontaktinformationen).</li>
 *   <li>Speichert Informationen zur Praktikumsstelle (z. B. Name, Adresse, Ansprechpartner).</li>
 *   <li>Ermöglicht die Verwaltung des Status eines Antrags (z. B. "In Bearbeitung", "Akzeptiert").</li>
 *   <li>Speichert Informationen zu Zeiträumen und Tätigkeiten des Praktikums.</li>
 * </ul>

 * <h2>Autor</h2>
 * <p>Gabriela Goncalvez</p>
 */


@Data
@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table (name = "praktikumsantrag") // Name der Datenbanktabelle

public class Praktikumsantrag {

    /**
     * Eindeutige ID des Antrags (Primärschlüssel).
     * Die ID wird automatisch generiert.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long antragsID;
    /**
     * Version des Antrags, Standardwert ist 1.
     * -@Column: Definiert die Spalteneigenschaften in der Datenbank.
     */
    @Column(name = "antrags_version", nullable = false, columnDefinition = "int default 1")
    private int antragsVersion = 1;

    /** Matrikelnummer der Studentin/des Studenten. */
    private String matrikelnummer;

    /** Nachname der Studentin/des Studenten. */
    private String nameStudentin;

    /** Vorname der Studentin/des Studenten. */
    private String vornameStudentin;

    /**
     * Geburtsdatum der Studentin/des Studenten.
     * -@JsonFormat: Definiert das Datumsformat.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate gebDatumStudentin;

    /** Straße und Hausnummer der Adresse der Studentin. */
    @Column(name = "adresse_studentin")
    private String strasseHausnummerStudentin;

    /** Postleitzahl der Adresse der Studentin. */
    private Integer plzStudentin;

    /** Ort der Adresse der Studentin. */
    private String ortStudentin;

    /** Telefonnummer der Studentin. */
    private String telefonnummerStudentin;

    /** E-Mail-Adresse der Studentin. */
    private String emailStudentin;

    /** Vorschlag für den Praktikumsbetreuer an der Hochschule. */
    private String vorschlagPraktikumsbetreuerIn;

    /** Semester, in dem das Praktikum stattfinden soll. */
    private String praktikumssemester;

    /** Aktuelles Studiensemester der Studentin/des Studenten. */
    private Integer studiensemester;

    /** Studiengang der Studentin/des Studenten. */
    private String studiengang;

    /** Gibt an, ob das Praktikum im Ausland stattfindet. */
    private Boolean auslandspraktikum;

    /**
     * Datum der Antragstellung.
     * -@JsonFormat: Definiert das Datumsformat.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate datumAntrag;

    /**
     * Status des Antrags (z. B. IN_BEARBEITUNG, ABGELEHNT).
     * -@Enumerated: Speichert den Enum-Wert als lesbaren String in der Datenbank.
     */
    @Enumerated(EnumType.STRING)
    private StatusAntrag statusAntrag;

    /** Name der Praktikumsstelle. */
    private String namePraktikumsstelle;

    /** Straße der Praktikumsstelle. */
    private String strassePraktikumsstelle;

    /** Postleitzahl der Praktikumsstelle. */
    private Integer plzPraktikumsstelle;

    /** Ort der Praktikumsstelle. */
    private String ortPraktikumsstelle;

    /** Bundesland der Praktikumsstelle. */
    private String bundeslandPraktikumsstelle;

    /** Land der Praktikumsstelle. */
    private String landPraktikumsstelle;

    /** Ansprechpartner bei der Praktikumsstelle. */
    private String ansprechpartnerPraktikumsstelle;

    /** Telefonnummer der Praktikumsstelle. */
    private String telefonPraktikumsstelle;

    /** E-Mail-Adresse der Praktikumsstelle. */
    private String emailPraktikumsstelle;

    /** Abteilung, in der das Praktikum stattfindet. */
    private String abteilung;

    /** Beschreibung der Tätigkeiten im Praktikum. */
    private String taetigkeit;

    /**
     * Startdatum des Praktikums.
     * -@JsonFormat: Definiert das Datumsformat.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate startdatum;

    /**
     * Enddatum des Praktikums.
     * -@JsonFormat: Definiert das Datumsformat.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate enddatum;
}
