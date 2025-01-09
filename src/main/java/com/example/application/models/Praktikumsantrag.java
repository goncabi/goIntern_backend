package com.example.application.models;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;

@Data
@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table (name = "praktikumsantrag")
public class Praktikumsantrag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long antragsID;
    /*
     Diese Annotation weist die Datenbank an, die ID automatisch zu generieren, wenn ein neuer Eintrag erstellt wird.
     Die Strategie GenerationType.IDENTITY ist dabei eine der gebräuchlichsten und sorgt dafür,
     dass jede ID in der Reihenfolge (1, 2, 3, ...) generiert wird.*/


    // @ManyToOne
    // @JoinColumn

    private String matrikelnummer;


    private String nameStudentin;


    private String vornameStudentin;


    private LocalDate gebDatumStudentin;

    private String adresseStudentin;

    private Integer plzStudentin;

    private String ortStudentin;

    private String bundesland;

    private String telefonnummerStudentin;


    private String emailStudentin;

    private String vorschlagPraktikumsbetreuerIn;


    private String praktikumssemester;

    private Integer studiensemester;

    private String studiengang;

    private Boolean auslandspraktikum;

    private LocalDate datumAntrag;

    @Enumerated(EnumType.ORDINAL) // besser in EnumType.STRING ändern, damit es in der Datenbank immer als unklare Zahl gespeichert wird
    private StatusAntrag statusAntrag;


    private String namePraktikumsstelle;


    private String strassePraktikumsstelle;


    private Integer plzPraktikumsstelle;


    private String ortPraktikumsstelle;


    private String landPraktikumsstelle;

    private String ansprechpartnerPraktikumsstelle;


    private String telefonPraktikumsstelle;

    private String emailPraktikumsstelle;

    private String abteilung;


    private String taetigkeit;


    private LocalDate startdatum;


    private LocalDate enddatum;

    //hinzufügen sobald json geändert wurde und sobald datenbank geändert wurde
//    @Enumerated(EnumType.STRING)
//    private Arbeitswoche arbeitswoche;
//
//    private Integer arbeitsTage;


}

