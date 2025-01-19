package com.example.application.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "posters")
@Setter @Getter
public class Poster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String matrikelnummer;
    private String fileName;
    private String uploadTime;
    @Enumerated(EnumType.STRING)
    private StatusPoster status;

    @Lob
    private byte[] posterPDF;

    public Poster() { }

    public Poster(String matrikelnummer, String fileName, byte[] posterPDF) {
        this.matrikelnummer = matrikelnummer;
        this.fileName = fileName;
        this.posterPDF = posterPDF;
        this.uploadTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
}
