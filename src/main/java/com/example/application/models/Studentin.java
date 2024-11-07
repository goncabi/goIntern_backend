package com.example.application.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "studentin")
public class Studentin {

    @Id
    private String matrikelnummer;
    private String passwort;

// private Praktikumsantrag praktikumsantrag; //noch fixen






}
