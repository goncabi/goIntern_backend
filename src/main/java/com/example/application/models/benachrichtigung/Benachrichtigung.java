package com.example.application.models.benachrichtigung;

import lombok.Getter;

import java.util.Date;

@Getter
public class Benachrichtigung {

    private String nachricht;
    private Date datum;
    private LeseStatus leseStatus;

    public Benachrichtigung(String nachricht, Date datum, LeseStatus leseStatus) {
        this.nachricht = nachricht;
        this.datum = datum;
        this.leseStatus = leseStatus;
    }
}
