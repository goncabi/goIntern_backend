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

    @Id
    private String matrikelnummer;
    private String password;
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;

    public Studentin() {
    }

    public Studentin(String matrikelnummer, String password, AppUserRole appUserRole) {
        this.matrikelnummer = matrikelnummer;
        this.password = password;
        this.appUserRole = appUserRole;
    }
}

