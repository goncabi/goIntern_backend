package com.example.application.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table
public class Praktikumsbeauftragter {

    @Id
    private String nachname;

    private String vorname;
    private String passwort;
}
