package com.example.application.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Table
@Getter
public class Sicherheitsantwort {

    @Id
    @GeneratedValue
    private Long antwortID;
    private int frage;
    private String matrikelnummer;
    private String antwort;

    public Sicherheitsantwort(int frage, String matrikelnummer, String antwort) {
        this.frage = frage;
        this.matrikelnummer = matrikelnummer;
        this.antwort = antwort;
    }

    public Sicherheitsantwort() {
    }
}
