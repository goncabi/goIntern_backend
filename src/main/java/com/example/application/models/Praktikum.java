package com.example.application.models;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
public class Praktikum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDatum;
    private LocalDate endDatum;
    private String status;


    public LocalDate getEndDatum() {
        return endDatum;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getStartDatum() {
        return startDatum;
    }

    public String getStatus() {
        return status;
    }

    public void setEndDatum(LocalDate endDatum) {
        this.endDatum = endDatum;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStartDatum(LocalDate startDatum) {
        this.startDatum = startDatum;
    }

    // Antrag beantragen
    public void beantragen() {
        this.status = "Beantragt";
        System.out.println("Praktikum beantragt: " + this.id);
    }

    // Antrag bestätigen
    public void bestaetigen() {
        this.status = "Bestätigt";
        System.out.println("Praktikum bestätigt: " + this.id);
    }

    // Antrag ablehnen mit Notiz
    public void ablehnen(String notiz) {
        this.status = "Abgelehnt";
        System.out.println("Praktikum abgelehnt: " + this.id + ". Notiz: " + notiz);
    }

  /*  // Praktikum löschen
    public void loeschen() {
        System.out.println("Praktikum gelöscht: " + this.id);
    }

    // Status zurückgeben
    public String getStatus() {
        return this.status;
    }*/
}