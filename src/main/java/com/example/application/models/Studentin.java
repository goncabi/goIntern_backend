package com.example.application.models;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Studentin {
    @Id
    private String matrikelnummer;
    private String name;
    private String vorname;
    private LocalDate geburtsdatum;
    private String strasse;
    private String plzUndOrt;
    private String telefonnummer;
    private String email;
    private String praktikumBetreuer;
    private String semester;
    private String lehrveranstaltung;
    private String fehlendeLeistungsnachweise;
    private Boolean ausnahmeZulassung;
    private LocalDate unterschriftDatum;

    public Boolean getAusnahmeZulassung() {
        return ausnahmeZulassung;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public String getFehlendeLeistungsnachweise() {
        return fehlendeLeistungsnachweise;
    }

    public String getLehrveranstaltung() {
        return lehrveranstaltung;
    }

    public String getMatrikelnummer() {
        return matrikelnummer;
    }

    public String getName() {
        return name;
    }

    public String getPlzUndOrt() {
        return plzUndOrt;
    }

    public String getPraktikumBetreuer() {
        return praktikumBetreuer;
    }

    public String getSemester() {
        return semester;
    }

    public String getStrasse() {
        return strasse;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public LocalDate getUnterschriftDatum() {
        return unterschriftDatum;
    }

    public String getVorname() {
        return vorname;
    }

    public void setAusnahmeZulassung(Boolean ausnahmeZulassung) {
        this.ausnahmeZulassung = ausnahmeZulassung;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFehlendeLeistungsnachweise(String fehlendeLeistungsnachweise) {
        this.fehlendeLeistungsnachweise = fehlendeLeistungsnachweise;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public void setLehrveranstaltung(String lehrveranstaltung) {
        this.lehrveranstaltung = lehrveranstaltung;
    }

    public void setMatrikelnummer(String matrikelnummer) {
        this.matrikelnummer = matrikelnummer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlzUndOrt(String plzUndOrt) {
        this.plzUndOrt = plzUndOrt;
    }

    public void setPraktikumBetreuer(String praktikumBetreuer) {
        this.praktikumBetreuer = praktikumBetreuer;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public void setUnterschriftDatum(LocalDate unterschriftDatum) {
        this.unterschriftDatum = unterschriftDatum;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }
    // Getter, Setter, und Methoden wie login(), logout() etc.
    // Antrag senden
   /* public void antragSenden(Praktikum praktikum) {
        // Logik zum Senden eines Antrags
        System.out.println("Antrag wurde gesendet: " + praktikum.getId());
    }*/
}