package com.example.application.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Data
@Entity
@Table(name = "studentin")
public class Studentin {

    @Getter
    @Id
    @Column(name = "matrikelnummer", unique = true, nullable = false)
    private String matrikelnummer;
    private String password;
    private boolean derzeitImPraktikum;
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;

    public Studentin() {}

    public Studentin(String matrikelnummer, String password, AppUserRole appUserRole) {
        this.matrikelnummer = matrikelnummer;
        this.password = password;
        this.appUserRole = appUserRole;
    }
}

