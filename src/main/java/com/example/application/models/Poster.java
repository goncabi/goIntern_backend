package com.example.application.models;

import jakarta.persistence.*;

//Hier eine Tabelle für Poster erstellt, damit das Poster gespeichert werden kann:
@Entity
@Table(name="poster2")
public class Poster {
    // Hier Objektvariablen als Spalten erstellen und Primary Key zuweisen
    @Id
    @Column(name = "matrikelnummer", unique = true, nullable = false)
    private String matrikelnummer;
    private String fileName;

    //Lob heiß Large Objekt
    @Lob
    private byte[] posterPDF;
}
