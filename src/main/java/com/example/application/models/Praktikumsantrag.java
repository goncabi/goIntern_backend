package com.example.application.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


@Entity
public class Praktikumsantrag {

    @Id
    private int praktikumsantragsnr;

   // @JdbcTypeCode(SqlTypes.NVARCHAR)
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

    private boolean vomPBBestaetigt;
    //FRAGE: wie mit Datum und Unterschrift von PB umgehen?

    private boolean vonAusbildungstelleBestatetigt;
    //FRAGE wie mit Datum und Unterschrift von Ausbildungstelle umgehen? + Namen der Studi eintragen?

    //Frage wie damit umgehen, dass Nachweis an das Prüfungsamt gesendet werden soll?
    //Frage wie damit umgehen dass die Praktikumsbeurteilung und Bericht dem PB vorgelegen hat und den Anforderungen entspricht und PB mit Datum und Unterschirft unterschrieben hat?
}
