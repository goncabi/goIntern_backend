package com.example.application.services;

import com.example.application.models.Praktikumsantrag;
import com.example.application.models.Status_Antrag;
import com.example.application.repositories.PraktikumsantragRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PraktikumsantragService {

    @Autowired
    private PraktikumsantragRepository praktikumsantragRepository;

    public Praktikumsantrag antragSpeichern(Praktikumsantrag antrag) {
        //Methode zum Speichern von Praktikumsantragdaten
         antrag.setStatusAntrag(Status_Antrag.INBEARBEITUNG); //Status ändert sich nach Praktikumsantragausgang
         return praktikumsantragRepository.save(antrag);
    }
}

/*
 Die Service-Schicht übernimmt hier die zentrale Logik und kümmert sich um  die Verwaltung der Praktikumsanträge.
Stellt Endpunkte für CRUD-Operationen bereit, damit das Frontend die Daten über HTTP  abrufen und verwalten kann.
REST-Methoden: `POST`, `GET`, `PUT`, `DELETE`
Diese Struktur hält die Geschäftslogik zentral und gut organisiert, sodass das Vaadin-Frontend, später problemlos mit den Anträgen arbeiten kann.
Service-Schicht verwaltet die zentrale Geschäftslogik, macht den Code wiederverwendbar und erleichtert Wartung und Erweiterung.
 */
