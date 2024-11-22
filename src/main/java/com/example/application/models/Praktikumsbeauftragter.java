package com.example.application.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "praktikumsbeauftragter")
public class Praktikumsbeauftragter {


    @Id
    private String username;
    private String passwort;
    @Enumerated(EnumType.STRING)
    private AppUserRole ADMIN;


    public Praktikumsbeauftragter() {

    }

    public Praktikumsbeauftragter(String username, String passwort, AppUserRole admin) {
        this.username = username;
        this.passwort = passwort;
        this.ADMIN = admin;

    }
}
