package com.example.application.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @Column (name = "adresse_studentin")
    private String strasseHausnummerStudentin;

    private Integer plzStudentin;

    private String ortStudentin;

    private String telefonnummerStudentin;


    private String emailStudentin;

    private String vorschlagPraktikumsbetreuerIn;


    private String praktikumssemester;

    private Integer studiensemester;

    private String studiengang;

    private Boolean auslandspraktikum;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate datumAntrag;

    @Enumerated(EnumType.ORDINAL)
    private StatusAntrag statusAntrag;


    private String namePraktikumsstelle;


    private String strassePraktikumsstelle;


    private Integer plzPraktikumsstelle;


    private String ortPraktikumsstelle;

    private String bundeslandPraktikumsstelle;

    private String landPraktikumsstelle;

    private String ansprechpartnerPraktikumsstelle;


    private String telefonPraktikumsstelle;

    private String emailPraktikumsstelle;

    private String abteilung;


    private String taetigkeit;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate startdatum;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate enddatum;


}

