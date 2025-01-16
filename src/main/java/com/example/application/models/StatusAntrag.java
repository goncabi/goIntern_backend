package com.example.application.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
/**
 * Repräsentiert den Status eines Praktikumsantrags.
 * <p>
 * Dieses Enum enthält die verschiedenen Status, die ein Praktikumsantrag haben kann,
 * und stellt Methoden bereit, um lesbare Beschreibungen zu erhalten oder zu konvertieren.
 * </p>
 */
public enum StatusAntrag {

    /** Der Antrag wurde gespeichert, aber noch nicht eingereicht. */
    GESPEICHERT("Gespeichert"),

    /** Der Antrag wurde eingereicht und wartet auf Bearbeitung. */
    EINGEREICHT("Antrag eingereicht"),

    /** Der Antrag wurde abgelehnt. */
    ABGELEHNT("Abgelehnt"),

    /** Der Antrag wurde zugelassen. */
    ZUGELASSEN("Zugelassen"),

    /** Derzeit im Praktikum. */
    IMPRAKTIKUM("Derzeit im Praktikum"),

    /** Das Praktikum wurde erfolgreich absolviert. */
    ABSOLVIERT("Absolviert"),

    /** Das Praktikum wurde abgebrochen. */
    ABGEBROCHEN("Praktikum abgebrochen");

    private final String leserlicherStatus;

    /**
     * Konstruktor für die Zuordnung eines lesbaren Statuswerts.
     *
     * @param leserlicherStatus Der lesbare Status.
     */
    StatusAntrag(String leserlicherStatus) {
        this.leserlicherStatus = leserlicherStatus;
    }

    /**
     * Gibt die lesbare Beschreibung des Status zurück.
     *
     * @return Der lesbare Status als String.
     */
    @JsonValue
    public String umwandelnInLeserlichenStatus() {
        return leserlicherStatus;
    }

    /**
     * Wandelt eine lesbare Beschreibung in das entsprechende Enum-Objekt um.
     *
     * @param text Der lesbare Status.
     * @return Das passende {@link StatusAntrag}-Objekt.
     * @throws IllegalArgumentException Falls der übergebene Text ungültig ist.
     */
    @JsonCreator
    public static StatusAntrag fromString(String text) {
        for (StatusAntrag status : StatusAntrag.values()) {
            if (status.leserlicherStatus.equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Ungültiger Status: " + text);
    }
}
