package com.example.application.services;

import com.example.application.models.Praktikumsantrag;
import com.example.application.models.Status_Antrag;
import com.example.application.repositories.PraktikumsantragRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.List;

@Service
@Validated

public class PraktikumsantragService {

    @Autowired
    private PraktikumsantragRepository praktikumsantragRepository;

    @Autowired
    private PBService pbService;



    // Methode zur Überprüfung, ob ein Antrag mit der Matrikelnummer bereits existiert
    public boolean antragVorhanden(String matrikelnummer) {
        return praktikumsantragRepository.findByMatrikelnummer(matrikelnummer).isPresent();
    }

    // Methode zur Erstellung eines neuen Antrags, wenn keiner vorhanden ist
    public String antragStellen(@Valid Praktikumsantrag antrag) {
        if (antragVorhanden(String.valueOf(antrag.getMatrikelnummer()))) {
            return "Es ist bereits ein Antrag vorhanden.";
        }
        antrag.setStatusAntrag(Status_Antrag.GESPEICHERT);
        praktikumsantragRepository.save(antrag);
        return "Antrag erfolgreich angelegt.";
    }

    // Methode zur Bearbeitung eines bestehenden Antrags
    public String antragBearbeiten(String matrikelnummer, @Valid Praktikumsantrag updatedAntrag) {
        Optional<Praktikumsantrag> existingAntrag = praktikumsantragRepository.findByMatrikelnummer(matrikelnummer);
        if (existingAntrag.isPresent()) {
            Praktikumsantrag antrag = existingAntrag.get();
            // hier müssten dann die Bearbeitungen am Antrag eingearbeitet werden
            antrag.setStatusAntrag(updatedAntrag.getStatusAntrag());
            praktikumsantragRepository.save(antrag);
            return "Antrag erfolgreich bearbeitet.";
        }
        return "Kein vorhandener Antrag mit dieser Matrikelnummer gefunden.";
    }

    public void antragLoeschen(Long id) {
        Optional<Praktikumsantrag> praktikumsantragDB = praktikumsantragRepository.findById(id);// Es wird aus der Datenbank der Praktikumsantrag mit der ID <id> geholt
        if (praktikumsantragDB.isEmpty()) {
            throw new RuntimeException("Praktikumsantrag mit der ID: " + id + " ist nicht vorhanden und kann nicht gelöscht werden");
        }
        praktikumsantragRepository.deleteById(id);
    }

    public void antragUebermitteln(Praktikumsantrag antrag) {
        if (!antragVorhanden(String.valueOf(antrag.getMatrikelnummer()))) {
            throw new IllegalArgumentException("Kein Antrag mit der Matrikelnummer " + antrag.getMatrikelnummer() + " gefunden.");
        }
         antrag.setStatusAntrag(Status_Antrag.UEBERMITTELT);

        //PBService kümmert sich um die Benachrichtigung
        pbService.antragUebermitteln(antrag);
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





