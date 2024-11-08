package com.example.application.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Sicherheitsantwort {

    @Id
    @GeneratedValue
    private Long antwortID;

    @ManyToOne
    @JoinColumn
    private Sicherheitsfrage frage;

    @ManyToOne
    @JoinColumn
    private Studentin matrikelnummer;

    private String antwort;
}
