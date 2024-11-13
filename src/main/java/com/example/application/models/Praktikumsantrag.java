package com.example.application.models;

import jakarta.validation.constraints.*;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /*
     Diese Annotation weist die Datenbank an, die ID automatisch zu generieren, wenn ein neuer Eintrag erstellt wird.
     Die Strategie GenerationType.IDENTITY ist dabei eine der gebräuchlichsten und sorgt dafür,
     dass jede ID in der Reihenfolge (1, 2, 3, ...) generiert wird.*/
    private Long antragsID;

    @ManyToOne
    @JoinColumn
    private Studentin matrikelnummer;

    @NotBlank(message = "Der Name darf nicht leer sein")
    @Size(max = 20, message = "Der Name darf maximal 20 Zeichen lang sein")
    private String nameStudentin;

    @NotBlank(message = "Der Vorname darf nicht leer sein")
    @Size(max = 15, message = "Der Vorname darf maximal 15 Zeichen lang sein")
    private String vornameStudentin;

    @NotNull(message = "Das Geburtsdatum darf nicht leer sein")
    private LocalDate gebDatumStudentin;

    @NotBlank(message = "Die Straße darf nicht leer sein")
    private String strasseStudentin;

    @Min(value = 1, message = "Die Hausnummer muss positiv sein")
    @Max(value = 999, message = "Die Hausnummer darf maximal 3 Ziffern haben")
    private Integer hausnummerStudentin;

    @NotNull(message = "Die Postleitzahl darf nicht leer sein")
    @Min(value = 10000, message = "Die Postleitzahl muss genau 5 Ziffern haben")
    @Max(value = 99999, message = "Die Postleitzahl muss genau 5 Ziffern haben")
    private Integer plzStudentin;

    @NotBlank(message = "Der Ort darf nicht leer sein")
    @Size(max = 15, message = "Der Ort darf maximal 15 Zeichen lang sein")
    private String ortStudentin;

    @NotBlank(message = "Die Telefonnummer darf nicht leer sein")
    @Size(max = 15, message = "Die Telefonnummer darf maximal 15 Zeichen lang sein")
    private String telefonnummerStudentin;

    @NotBlank(message = "Die E-Mail darf nicht leer sein")
    @Email(message = "Die E-Mail muss ein gültiges Format haben")
    private String emailStudentin;

    @Size(max = 50, message = "Der Vorschlag des Praktikumsbetreuers darf maximal 50 Zeichen lang sein")
    private String vorschlagPraktikumsbetreuerIn;

    @NotBlank(message = "Das Praktikumssemester darf nicht leer sein")
    @Size(max = 10, message = "Das Praktikumssemester darf maximal 10 Zeichen lang sein")
    private String praktikumssemester;

    @NotNull(message = "Das Studiensemester darf nicht leer sein")
    private Integer studiensemester;

    @NotBlank(message = "Der Studiengang darf nicht leer sein")
    @Size(max = 50, message = "Der Studiengang darf maximal 50 Zeichen lang sein")
    private String studiengang;

    @Size(max = 500, message = "Die begleitenden Lehrveranstaltungen dürfen maximal 500 Zeichen lang sein")
    private String begleitendeLehrVeranstaltungen;

    private Boolean voraussetzendeLeistungsnachweise;

    @Size(max = 500, message = "Die fehlenden Leistungsnachweise dürfen maximal 500 Zeichen lang sein")
    private String fehlendeLeistungsnachweise;

    private Boolean ausnahmeZulassung;

    @NotNull(message = "Das Antragsdatum darf nicht leer sein")
    private LocalDate datumAntrag;

    @Enumerated(EnumType.STRING)
    private Status_Antrag statusAntrag;

    @NotBlank(message = "Der Name der Praktikumsstelle darf nicht leer sein")
    @Size(max = 50, message = "Der Name der Praktikumsstelle darf maximal 50 Zeichen lang sein")
    private String namePraktikumsstelle;

    @NotBlank(message = "Die Straße der Praktikumsstelle darf nicht leer sein")
    @Size(max = 50, message = "Die Straße der Praktikumsstelle darf maximal 50 Zeichen lang sein")
    private String strassePraktikumsstelle;

    @NotNull(message = "Die Postleitzahl der Praktikumsstelle darf nicht leer sein")
    @Min(value = 10000, message = "Die Postleitzahl muss genau 5 Ziffern haben")
    @Max(value = 99999, message = "Die Postleitzahl muss genau 5 Ziffern haben")
    private Integer plzPraktikumsstelle;

    @NotBlank(message = "Der Ort der Praktikumsstelle darf nicht leer sein")
    @Size(max = 20, message = "Der Ort darf maximal 20 Zeichen lang sein")
    private String ortPraktikumsstelle;

    @NotBlank(message = "Das Land der Praktikumsstelle darf nicht leer sein")
    @Size(max = 30, message = "Das Land darf maximal 30 Zeichen lang sein")
    private String landPraktikumsstelle;

    @NotBlank(message = "Der Ansprechpartner darf nicht leer sein")
    @Size(max = 30, message = "Der Ansprechpartner darf maximal 30 Zeichen lang sein")
    private String ansprechpartnerPraktikumsstelle;

    @NotBlank(message = "Die Telefonnummer darf nicht leer sein")
    @Size(max = 15, message = "Die Telefonnummer darf maximal 15 Zeichen lang sein")
    private String telefonPraktikumsstelle;

    @NotBlank(message = "Die E-Mail darf nicht leer sein")
    @Email(message = "Die E-Mail muss ein gültiges Format haben")
    private String emailPraktikumsstelle;

    @NotBlank(message = "Die Abteilung darf nicht leer sein")
    @Size(max = 15, message = "Die Abteilung darf maximal 15 Zeichen lang sein")
    private String abteilung;

    @NotBlank(message = "Die Tätigkeit darf nicht leer sein")
    @Size(max = 30, message = "Die Tätigkeit darf maximal 30 Zeichen lang sein")
    private String taetigkeit;

    @NotNull(message = "Das Startdatum darf nicht leer sein")
    private LocalDate startdatum;

    @NotNull(message = "Das Enddatum darf nicht leer sein")
    private LocalDate enddatum;
}