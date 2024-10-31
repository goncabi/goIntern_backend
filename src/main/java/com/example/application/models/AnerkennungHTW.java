package com.example.application.models;


import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class AnerkennungHTW {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Praktikum praktikum;

    @ManyToOne
    private Praktikumsbeauftragter praktikumsbeauftragter;

    private LocalDate anerkennungDatum;
    private String anerkennungUnterschrift;

    public AnerkennungHTW() {}

    public AnerkennungHTW(Praktikum praktikum, Praktikumsbeauftragter praktikumsbeauftragter) {
        this.praktikum = praktikum;
        this.praktikumsbeauftragter = praktikumsbeauftragter;
    }

    public LocalDate getAnerkennungDatum() {
        return anerkennungDatum;
    }

    public String getAnerkennungUnterschrift() {
        return anerkennungUnterschrift;
    }

    public Praktikum getPraktikum() {
        return praktikum;
    }

    public void setPraktikum(Praktikum praktikum) {
        this.praktikum = praktikum;
    }

    public Praktikumsbeauftragter getPraktikumsbeauftragter() {
        return praktikumsbeauftragter;
    }

    public void setPraktikumsbeauftragter(Praktikumsbeauftragter praktikumsbeauftragter) {
        this.praktikumsbeauftragter = praktikumsbeauftragter;
    }

    public void setAnerkennungDatum(LocalDate anerkennungDatum) {
        this.anerkennungDatum = anerkennungDatum;
    }

    public void setAnerkennungUnterschrift(String anerkennungUnterschrift) {
        this.anerkennungUnterschrift = anerkennungUnterschrift;
    }

    public void setId(Long id) {
        this.id = id;
    }
}