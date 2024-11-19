package com.example.application.services;

import com.example.application.models.Praktikumsantrag;
import com.example.application.models.Status_Antrag;
import com.example.application.models.Studentin;
import com.example.application.repositories.PraktikumsantragRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Service
@Validated

public class PraktikumsantragService {

    @Autowired
    private PraktikumsantragRepository praktikumsantragRepository;


    // Methode zur Überprüfung, ob ein Antrag mit der Matrikelnummer bereits existiert
    public boolean antragVorhanden(String matrikelnummer) {
        return praktikumsantragRepository.findByMatrikelnummer(matrikelnummer).isPresent();
    }



//    public String antragStellen(@Valid Praktikumsantrag antrag) {
//        Optional<Praktikumsantrag> existingAntrag = praktikumsantragRepository.findByMatrikelnummer(antrag.getMatrikelnummer());
//
//        if (existingAntrag.isPresent()) {
//            // Antrag mit dieser Matrikelnummer existiert bereits
//            return "Antrag weiter bearbeiten; kein zweiter Antrag möglich";
//        } else {
//            // Kein Antrag vorhanden, also speichern und Methode 'anlegen' verwenden
//            antrag.setStatusAntrag(Status_Antrag.INBEARBEITUNG);
//            praktikumsantragRepository.save(antrag);
//            return "Antrag erfolgreich angelegt.";
//        }
//    }

}

/*
 Die Service-Schicht übernimmt hier die zentrale Logik und kümmert sich um  die Verwaltung der Praktikumsanträge.
Stellt Endpunkte für CRUD-Operationen bereit, damit das Frontend die Daten über HTTP  abrufen und verwalten kann.
REST-Methoden: `POST`, `GET`, `PUT`, `DELETE`
Diese Struktur hält die Geschäftslogik zentral und gut organisiert, sodass das Vaadin-Frontend, später problemlos mit den Anträgen arbeiten kann.
Service-Schicht verwaltet die zentrale Geschäftslogik, macht den Code wiederverwendbar und erleichtert Wartung und Erweiterung.
 */
