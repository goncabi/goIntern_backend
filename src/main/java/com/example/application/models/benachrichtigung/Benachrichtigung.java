package com.example.application.models.benachrichtigung;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import java.util.Date;

@Data
@Entity
@Table(name = "nachricht")
@Getter
public class Benachrichtigung {

    @Id
    @GeneratedValue
    private Long nachrichtId;

    private String nachricht;
    private Date datum;
    @Enumerated(EnumType.STRING)
    private LeseStatus leseStatus;
    private String empfaenger;

    public Benachrichtigung() {}

    public Benachrichtigung(String nachricht, Date datum, LeseStatus leseStatus, String empfaenger_in) {
        this.nachricht = nachricht;
        this.datum = datum;
        this.leseStatus = leseStatus;
        this.empfaenger = empfaenger_in;
    }


}
