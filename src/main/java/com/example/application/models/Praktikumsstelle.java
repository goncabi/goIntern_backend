package com.example.application.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Praktikumsstelle {

    @Id
   private Long praktikumsstellenID;

    private String praktikumsstellenName;
    private String strass;
    private int plz;
    private String ort;
    private String land;
    private String ansprechpartnerPraktikumsstelle;
    private String telefon;
    private String email;
    private Date startdatum;
    private Date enddatum;
    private String abteilung; //Einsatzbereich/Abteilung
    private String taetigkeitDesPraktikanten;

}
