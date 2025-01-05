package com.example.application.models;

import com.fasterxml.jackson.annotation.JsonCreator;
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

    @JsonCreator
    public static StatusAntrag fromString(String text) {
        for (StatusAntrag status : StatusAntrag.values()) {
            if (status.leserlicherStatus.equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Ungültiger Status: " + text);
    }


    //hier eine methode, die später bei klickevent gebraucht wird, wenn pb auf antrag
    //anzeigen klickt ändert sich status bei studentin und pb von eingereicht auf in bearbeitung

    public static StatusAntrag nextStatus(StatusAntrag currentStatus) {
        if (currentStatus == EINGEREICHT) {
            return INBEARBEITUNG;
        }
        return currentStatus;
    }

}

