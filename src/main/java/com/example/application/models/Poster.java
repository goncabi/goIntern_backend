package com.example.application.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Repräsentiert ein Poster, dass von einer Studentin hochgeladen werden kann.
 * <p> Diese Klasse ist eine JPA-Entität, die in der Datenbank in der Tabelle
 * "posters" gespeichert wird. </p>
 */
@Entity
@Table(name = "posters")
@Setter @Getter
public class Poster {
    /**
     * Eindeutige Id des Poster, automatisch generiert
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Die Matrikelnummer der Studentin, welche das Poster hochlädt
     */
    private String matrikelnummer;
    /**
     * Dokumentenname
     */
    private String fileName;
    /**
     * Zeitpunkt des Uploads
     */
    private String uploadTime;
    /**
     * Pdf-Datei
     */
    @Lob
    private byte[] posterPDF;

    /**
     * Standardkonstruktor für die JPA.
     */
    public Poster() { }

    /**
     * Konstruktor für die Erstellung eines neuen Posters.
     * Der Zeitpunkt des Uploads wird automatisch auf heute gesetzt und formatiert.
     * @param matrikelnummer Matrikelnummer der Studentin
     * @param fileName Name der Datei
     * @param posterPDF Pdf-Datei
     */
    public Poster(String matrikelnummer, String fileName, byte[] posterPDF) {
        this.matrikelnummer = matrikelnummer;
        this.fileName = fileName;
        this.posterPDF = posterPDF;
        this.uploadTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
}
