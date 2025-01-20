package com.example.application.models;

public enum StatusPoster {
    /**
     * Das Poster wurde in der Datenbank gespeichert.
     */
    GESPEICHERT,
    /**
     * Das Poster wurde hochgeladen und an den PB übermittelt.
     */
    EINGEREICHT,
    /**
     * Das Poster wurde von dem PB akzeptiert.
     */
    GENEHMIGT,
    /**
     * Das Poster wurde von dem PB bemängelt und abgelehnt.
     */
    ABGELEHNT;
}
