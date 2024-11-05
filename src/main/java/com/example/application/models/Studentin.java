package com.example.application.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Studentin {

    @Id
    private String matrikelnummer;

    private String name;
    private String vorname;
    private String passwort;

    //hab hier die Variablen von Praktikumsantrag in die Studentinklasse getan, weil jeder Praktikumsantrag eine eigene Studentin hat.
    private Date gebDatum;
    private String strasse;
    private int hausnummer;
    private int plz;
    private String ort;
    private String telefonnummer;
    private String email;



}
