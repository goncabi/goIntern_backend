package com.example.application.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table
public class Sicherheitsfrage {

    @Id
    private long frageID;
    private String frage;

    public Sicherheitsfrage(int frageID, String frage) {
        this.frageID = frageID;
        this.frage = frage;
    }
    public Sicherheitsfrage() {

    }
}
