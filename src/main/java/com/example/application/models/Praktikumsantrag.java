package com.example.application.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Praktikumsantrag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)/*
     Diese Annotation weist die Datenbank an, die ID automatisch zu generieren, wenn ein neuer Eintrag erstellt wird.
     Die Strategie GenerationType.IDENTITY ist dabei eine der gebräuchlichsten und sorgt dafür,
     dass jede ID in der Reihenfolge (1, 2, 3, ...) generiert wird.*/
    private int antragsID;

    private String matrikelnummerStudentin;
    private String nameStudentin;
    private String vornameStudentin;
    private Date gebDatumStudentin;
    private String strasseStudentin;
    private int hausnummerStudentin;
    private int plzStudentin;
    private String ortStudentin;
    private String telefonnummerStudentin;
    private String emailStudentin;

    private String vorschlagPraktikumsbetreuerIn;
    private String praktikumssemester;
    private int studiensemester;
    private String studiengang;
    private String begleitendeLehrVeranstaltungen;

    private boolean voraussetzendeLeistungsnachweise;
    private String fehlendeLeistungsnachweise;
    private boolean ausnahmeZulassung;

    private Date datumAntrag;
    private Status status;

    private String namePraktikumsstelle;
    private String strassePraktikumsstelle;
    private int plzPraktikumsstelle;
    private String ortPraktikumsstelle;
    private String landPraktikumsstelle;
    private String ansprechpartnerPraktikumsstelle;
    private String telefonPraktikumsstelle;
    private String emailPraktikumsstelle;
    private String abteilung;
    private String taetigkeit;

    private Date startdatum;
    private Date enddatum;
}
