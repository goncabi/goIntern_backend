package com.example.application.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.Date;

@Data
@Entity
@Table(name = "praktikumsantrag")
public class Praktikumsantrag {

    @Id
    private int antragsID;

    @ManyToOne
    @JoinColumn
    private Studentin matrikelnummer;

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
