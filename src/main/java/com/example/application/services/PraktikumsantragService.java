package com.example.application.services;

import com.example.application.models.Praktikumsantrag;
import com.example.application.models.Status_Antrag;
import com.example.application.repositories.PraktikumsantragRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.List;
import java.util.Set;

@Service
@Validated

public class PraktikumsantragService {

    @Autowired
    private PraktikumsantragRepository praktikumsantragRepository;

    @Autowired
    private PBService pbService;
    private Validator validator;



    // Methode zur Überprüfung, ob ein Antrag mit der Matrikelnummer bereits existiert
    public boolean antragVorhanden(String matrikelnummer) {
        return praktikumsantragRepository.findByMatrikelnummer(matrikelnummer).isPresent();
    }

    // Methode zur Erstellung eines neuen Antrags, wenn keiner vorhanden ist
    public String antragErstellen(String matrikelnummer) {
        if (antragVorhanden(matrikelnummer)) {
            return "Es ist bereits ein Antrag vorhanden.";
        }
        Praktikumsantrag antrag = new Praktikumsantrag();
        antrag.setStatusAntrag(Status_Antrag.GESPEICHERT);
        praktikumsantragRepository.save(antrag);
        return "Antrag erfolgreich angelegt.";
    }


    public Praktikumsantrag antragAnzeigen(String matrikelnummer) {
        Optional<Praktikumsantrag> antrag = praktikumsantragRepository.findByMatrikelnummer(matrikelnummer);
        if(antrag.isEmpty()) {
                throw new RuntimeException("Kein Antrag mit der Matrikelnummer " + matrikelnummer + " vorhanden. Lege zuerst einen Antrag an.");
        }
        return antrag.get();

    }

    public String antragBearbeiten(String matrikelnummer, @Valid Praktikumsantrag antragVorBearbeitung) {
        // antragAnzeigen, damit ich Felder ansehen kann, bevor ich bearbeite
        Praktikumsantrag neuerAntrag = antragAnzeigen(matrikelnummer);

        // hier Werte beispielhaft aktualisieren...
        neuerAntrag.setAusnahmeZulassung(true);
        neuerAntrag.setStartdatum(antragVorBearbeitung.getStartdatum());
        neuerAntrag.setEnddatum(antragVorBearbeitung.getEnddatum());

        // Validierung des aktualisierten Antrags (weil nur valide aktualisierungen auch gespeichert werden sollen)
        Set<ConstraintViolation<Praktikumsantrag>> violations = validator.validate(neuerAntrag);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        // Speichert den aktualisierten Antrag in der Datenbank
        praktikumsantragRepository.save(neuerAntrag);
        neuerAntrag.setStatusAntrag(Status_Antrag.GESPEICHERT);
        return "Antrag erfolgreich bearbeitet.";
    }


    public void antragLoeschen(Long id) {
        Optional<Praktikumsantrag> praktikumsantragDB = praktikumsantragRepository.findById(id);// Es wird aus der Datenbank der Praktikumsantrag mit der ID <id> geholt
        if (praktikumsantragDB.isEmpty()) {
            throw new RuntimeException("Praktikumsantrag mit der ID: " + id + " ist nicht vorhanden und kann nicht gelöscht werden");
        }
        praktikumsantragRepository.deleteById(id);
    }

    public void antragUebermitteln(Praktikumsantrag antrag) {
        if(!antragVorhanden(String.valueOf(antrag.getMatrikelnummer()))) {
            throw new IllegalArgumentException(
                    "Kein Antrag mit der Matrikelnummer " + antrag.getMatrikelnummer() + " gefunden.");
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





