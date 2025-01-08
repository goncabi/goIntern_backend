package com.example.application.models.benachrichtigung;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
@Table(name = "benachrichtigung")
@Getter
public class Benachrichtigung {

    @Id
    @GeneratedValue
    private Long nachrichtId;

    private String nachricht;
    private String datum;
    @Enumerated(value = EnumType.STRING)
    private LeseStatus leseStatus;
    private String empfaenger;

    public Benachrichtigung() {}

    public Benachrichtigung(String nachricht, Date datum, LeseStatus leseStatus, String empfaenger_in) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String formattedDate = sdf.format(datum);
        this.nachricht = nachricht;
        this.datum = formattedDate;
        this.leseStatus = leseStatus;
        this.empfaenger = empfaenger_in;
    }
}
