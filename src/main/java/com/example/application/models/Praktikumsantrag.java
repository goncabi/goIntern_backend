package com.example.application.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.time.LocalDate;

@Data
@Entity
@Table(name = "praktikumsantrag")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Praktikumsantrag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)/*
     Diese Annotation weist die Datenbank an, die ID automatisch zu generieren, wenn ein neuer Eintrag erstellt wird.
     Die Strategie GenerationType.IDENTITY ist dabei eine der gebräuchlichsten und sorgt dafür,
     dass jede ID in der Reihenfolge (1, 2, 3, ...) generiert wird.*/
    private Long antragsID;

    @ManyToOne
    @JoinColumn
    private Studentin matrikelnummer;

    @NotNull
    @Size(max = 15)
    private String nameStudentin;

    @NotNull
    @Size(max = 15)
    private String vornameStudentin;

    @NotNull
    private LocalDate gebDatumStudentin;

    @NotNull
    private String strasseStudentin;

    @NotNull
    @Size(max = 3)
    private int hausnummerStudentin;

    @NotNull
    @Size(max = 5)
    private int plzStudentin;

    @NotNull
    @Size(max = 15)
    private String ortStudentin;

    @NotNull
    @Size(max = 15)
    private String telefonnummerStudentin;

    @NotNull
    @Email
    private String emailStudentin;

    @NotNull
    @Size(max = 50)
    private String vorschlagPraktikumsbetreuerIn;

    @NotNull
    @Size(max = 10)
    private String praktikumssemester;

    @NotNull
    @Size(max = 10)
    private int studiensemester;

    @NotNull
    @Size(max = 50)
    private String studiengang;

    @NotNull
    @Size(max = 500)
    private String begleitendeLehrVeranstaltungen;

    @NotNull
    private boolean voraussetzendeLeistungsnachweise;

    @NotNull
    @Size(max = 500)
    private String fehlendeLeistungsnachweise;

    @NotNull
    private boolean ausnahmeZulassung;

    @NotNull
    private LocalDate datumAntrag;

    @Enumerated(EnumType.STRING)
    private Status_Antrag statusAntrag;

    @NotNull
    @Size(max = 50)
    private String namePraktikumsstelle;

    @NotNull
    @Size(max = 50)
    private String strassePraktikumsstelle;

    @NotNull
    @Size(max = 5)
    private int plzPraktikumsstelle;

    @NotNull
    @Size(max = 20)

    private String ortPraktikumsstelle;

    @NotNull
    @Size(max = 30)
    private String landPraktikumsstelle;

    @NotNull
    @Size(max = 30)
    private String ansprechpartnerPraktikumsstelle;

    @NotNull
    @Size(max = 15)
    private String telefonPraktikumsstelle;

    @NotNull
    @Email
    private String emailPraktikumsstelle;

    @NotNull
    @Size(max = 15)
    private String abteilung;

    @NotNull
    @Size(max = 30)
    private String taetigkeit;

    @NotNull
    private LocalDate startdatum;

    @NotNull
    private LocalDate enddatum;
}
