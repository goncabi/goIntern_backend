package com.example.application.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Praktikumsbeauftragter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String abteilung;
    private String email;
    private String passwort;

    // Login-Methode
    public boolean login(String email, String passwort) {
        return this.email.equals(email) && this.passwort.equals(passwort);
    }

    // Logout-Methode
    public void logout() {
        // Eventuell Session oder Status zurücksetzen
        System.out.println("Praktikumsbeauftragter logged out.");
    }

  /*  // Anträge einsehen
    public List<Praktikum> antraegeEinsehen(List<Praktikum> praktika) {
        return praktika;
    }

    // Praktikum genehmigen
    public void genehmigen(Praktikum praktikum) {
        praktikum.setStatus("Genehmigt");
        System.out.println("Praktikum genehmigt: " + praktikum.getId());
    }

    // Praktikum ablehnen
    public void ablehnen(Praktikum praktikum, String notiz) {
        praktikum.setStatus("Abgelehnt");
        System.out.println("Praktikum abgelehnt: " + praktikum.getId() + ". Notiz: " + notiz);
    }

    // Benachrichtigung senden
    public void benachrichtigen(Praktikum neuerAntrag) {
        System.out.println("Benachrichtigung: Neuer Antrag von StudentIn für Praktikum " + neuerAntrag.getId());
    }*/

    public String getAbteilung() {
        return abteilung;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPasswort() {
        return passwort;
    }
}
