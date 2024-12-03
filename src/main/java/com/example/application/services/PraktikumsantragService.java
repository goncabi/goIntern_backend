package com.example.application.services;

import com.example.application.models.Praktikumsantrag;
import com.example.application.models.StatusAntrag;
import com.example.application.repositories.PraktikumsantragRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.List;

@Validated
@AllArgsConstructor
@Service

public class PraktikumsantragService {


    private final PraktikumsantragRepository praktikumsantragRepository;
    private final PBService pbService;

    // Methode zur Überprüfung, ob ein Antrag mit der Matrikelnummer bereits existiert
    public boolean antragVorhanden(String matrikelnummer) {
        return praktikumsantragRepository.findByMatrikelnummer(matrikelnummer).isPresent();
    }

    // Methode zur Erstellung eines neuen Antrags, wenn keiner vorhanden ist
    public void antragErstellen(String matrikelnummer) {
        if (matrikelnummer == null || matrikelnummer.isBlank()) {
            throw new IllegalArgumentException("Die Matrikelnummer darf nicht leer sein.");
        }

        if (antragVorhanden(matrikelnummer)) {
            throw new IllegalArgumentException("Es ist bereits ein Antrag vorhanden.");
        }
        Praktikumsantrag antrag = new Praktikumsantrag();
        antrag.setStatusAntrag(StatusAntrag.GESPEICHERT);
        praktikumsantragRepository.save(antrag);

    }

    public Praktikumsantrag antragAnzeigen(String matrikelnummer) {
        Optional<Praktikumsantrag> antrag = praktikumsantragRepository.findByMatrikelnummer(matrikelnummer);
        if(antrag.isEmpty()) {
                throw new RuntimeException("Kein Antrag mit der Matrikelnummer " + matrikelnummer + " vorhanden. Lege zuerst einen Antrag an.");
        }
        return antrag.get();

    }


    //transactional dient dazu, dass wenn beim bearbeiten etwas passiert
    // alle daten dann wieder zurück gesetzt werden auf den anfangszustand
    @Transactional
    //antrag kann nur nach antragAnzeigen bearbeitet werden
    public String antragBearbeiten(String matrikelnummer, Praktikumsantrag antragVorBearbeitung) {

        if(antragVorBearbeitung.getStatusAntrag() == StatusAntrag.GESPEICHERT || antragVorBearbeitung.getStatusAntrag() == StatusAntrag.ABGELEHNT) {

            // antragAnzeigen, damit ich Felder ansehen kann, bevor ich bearbeite

            Praktikumsantrag bearbeiteterAntrag = antragAnzeigen(matrikelnummer);

            // hier Werte beispielhaft aktualisieren...
            bearbeiteterAntrag.setAusnahmeZulassung(true);
            bearbeiteterAntrag.setStartdatum(antragVorBearbeitung.getStartdatum());
            bearbeiteterAntrag.setEnddatum(antragVorBearbeitung.getEnddatum());

            // Speichert den aktualisierten Antrag in der Datenbank
            praktikumsantragRepository.save(bearbeiteterAntrag);
            bearbeiteterAntrag.setStatusAntrag(StatusAntrag.GESPEICHERT);
            return "Antrag erfolgreich bearbeitet.";
        }
        else {
            return "Keine weitere Bearbeitung möglich, da Antrag bereits abgesendet";
        }
    }

    public void antragLoeschen(Long id) {
        Optional<Praktikumsantrag> praktikumsantragDB = praktikumsantragRepository.findById(id);// Es wird aus der Datenbank der Praktikumsantrag mit der ID <id> geholt
        if (praktikumsantragDB.isEmpty()) {
            throw new RuntimeException("Praktikumsantrag mit der ID: " + id + " ist nicht vorhanden und kann nicht gelöscht werden");
        }
        praktikumsantragRepository.deleteById(id);
    }

    public void antragUebermitteln(@Valid Praktikumsantrag antrag) {
        if (!antragVorhanden(antrag.getMatrikelnummer())) {
            throw new IllegalArgumentException("Kein Antrag mit der Matrikelnummer " + antrag.getMatrikelnummer() + " gefunden.");
        }
        if (antrag.getStatusAntrag() != StatusAntrag.GESPEICHERT) {
            throw new IllegalStateException("Nur gespeicherte Anträge können übermittelt werden.");
        }
        if (antrag.getStartdatum().isAfter(antrag.getEnddatum())) {
            throw new IllegalArgumentException("Das Startdatum darf nicht nach dem Enddatum liegen.");
        }

        pbService.antragUebermitteln(antrag);         //PBService kümmert sich um die Benachrichtigung

    }

    public List<Praktikumsantrag> getAllAntraege() {

        return praktikumsantragRepository.findAll();
    }
}


/*
 Die Service-Schicht übernimmt hier die zentrale Logik und kümmert sich um  die Verwaltung der Praktikumsanträge.
Stellt Endpunkte für CRUD-Operationen bereit, damit das Frontend die Daten über HTTP  abrufen und verwalten kann.
REST-Methoden: `POST`, `GET`, `PUT`, `DELETE`
Diese Struktur hält die Geschäftslogik zentral und gut organisiert, sodass das Vaadin-Frontend, später problemlos mit den Anträgen arbeiten kann.
Service-Schicht verwaltet die zentrale Geschäftslogik, macht den Code wiederverwendbar und erleichtert Wartung und Erweiterung.
 */





