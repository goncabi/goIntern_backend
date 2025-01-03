package com.example.application.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusAntrag {
    GESPEICHERT ("Gespeichert"), EINGEREICHT("Antrag eingereicht"), INBEARBEITUNG ("In Bearbeitung"),
    ABGELEHNT("Abgelehnt"), ZUGELASSEN("Zugelassen"), IMPRAKTIKUM("Derzeit im Praktikum"),
    ABSOLVIERT("Absolviert");


    private final String leserlicherStatus;

    StatusAntrag(String leserlicherStatus) {
        this.leserlicherStatus = leserlicherStatus;
    }

    @JsonValue
    public String umwandelnInLeserlichenStatus() {
        return leserlicherStatus;
    }

}

