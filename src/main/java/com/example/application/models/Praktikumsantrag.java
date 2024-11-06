package com.example.application.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;


@Entity
public class Praktikumsantrag {

    @Id
    private int praktikumsantragsnr;

    @OneToOne
    @JoinColumn(name = "studentinMatrikelnummer", referencedColumnName = "matrikelnummer")
    private Studentin studentin;


    private String vorschlagPraktikumsbetreuerIn;
    private String praktikumssemester;
    private int studiensemester;
    private String studiengang;
    private String titelPraxisBegleitLehrVeranstaltungen;

    private boolean leistungsnachweisVoraussetzungfuerPraktikum;
    private String fehlendeLeistungsnachweise;
    private boolean antragAusnahmezulassungGestellt;

    //FRAGE: wie mit Datum und Unterschrift von Studi umgehen?
    private Date datumderStudiUnterschrift;
    private boolean vonStudentinUnterschrieben;

    private boolean vomPBBestaetigt;
    //FRAGE: wie mit Datum und Unterschrift von PB umgehen?
    private Date datumdesPBUnterschrift;
    private boolean vonPBUnterschrieben;

    private boolean vonAusbildungstelleBestatetigt;
    //FRAGE wie mit Datum und Unterschrift von Ausbildungstelle umgehen? + Namen der Studi eintragen?
    private Date datumderAusbildungstelleUnterschrift;
    private boolean vonAusbildungstelleUnterschrieben;

    //Frage wie damit umgehen dass die Praktikumsbeurteilung und Bericht dem PB vorgelegen hat und den Anforderungen entspricht und PB mit Datum und Unterschirft unterschrieben hat?
    private Date datumdesPBUnterschrift2;
    private boolean vonPBUnterschrieben2;

    //Frage wie damit umgehen, dass Nachweis an das Prüfungsamt gesendet werden soll?
    private boolean anprufeungsAmtversendet;
}
