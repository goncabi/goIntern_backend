package com.example.application.services;

import com.example.application.models.ArbeitstageRechner;
import com.example.application.models.Arbeitswoche;
import com.example.application.models.Praktikumsantrag;
import com.example.application.models.StatusAntrag;
import com.example.application.repositories.PraktikumsantragRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;


@Validated
@AllArgsConstructor
@Service

public class PraktikumsantragService {


    private final PraktikumsantragRepository praktikumsantragRepository;
    private final PBService pbService;
    private final ArbeitstageRechner arbeitstageRechner = new ArbeitstageRechner();

    // Methode zur Überprüfung, ob ein Antrag mit der Matrikelnummer bereits existiert
    public boolean antragVorhanden(String matrikelnummer) {
        return praktikumsantragRepository.findByMatrikelnummer(matrikelnummer)
                .isPresent();
    }

    public Praktikumsantrag antragAnzeigen(String matrikelnummer) {
        Optional<Praktikumsantrag> antrag = praktikumsantragRepository.findByMatrikelnummer(matrikelnummer);
        if (antrag.isEmpty()) {
            throw new RuntimeException("Kein Antrag mit der Matrikelnummer " + matrikelnummer +
                    " vorhanden. Lege zuerst einen Antrag an.");
        }
        return antrag.get();
    }

    // Methode zur Erstellung eines neuen Antrags, wenn keiner vorhanden ist
    //oder: update der Daten, wenn Antrag schon vorhanden ist
    public void antragSpeichern(Praktikumsantrag antrag) {
        if (antrag.getMatrikelnummer() == null || antrag.getMatrikelnummer()
                .isBlank()) {
            throw new IllegalArgumentException("Die Matrikelnummer darf nicht leer sein.");
        }

        Optional<Praktikumsantrag> vorhandenerAntrag = praktikumsantragRepository.findByMatrikelnummer(antrag.getMatrikelnummer());

        if (vorhandenerAntrag.isPresent()) {
            Praktikumsantrag bestehenderAntrag = vorhandenerAntrag.get();

            // Prüfen, ob der Antrag den Status "GESPEICHERT" oder Status "ABGELEHNT" hat
            if ((bestehenderAntrag.getStatusAntrag() != StatusAntrag.GESPEICHERT) &&
                    (bestehenderAntrag.getStatusAntrag() != StatusAntrag.ABGELEHNT)) {
                throw new IllegalStateException("Anträge können nur bearbeitet werden, wenn sie noch nicht eingereicht sind, oder wenn sie bereits abgelehnt sind.");
            }

            // Felder aktualisieren
            updateAntragFields(bestehenderAntrag,
                    antrag);


            praktikumsantragRepository.save(bestehenderAntrag);
        } else {
            // Neuer Antrag speichern
            antrag.setStatusAntrag(StatusAntrag.GESPEICHERT);
            praktikumsantragRepository.save(antrag);
        }
    }


    public void antragLoeschen(String matrikelnummer) {
        Optional<Praktikumsantrag> praktikumsantragDB = praktikumsantragRepository.findByMatrikelnummer(matrikelnummer);// Es wird aus der Datenbank der Praktikumsantrag mit der ID <id> geholt
        if (praktikumsantragDB.isEmpty()) {
            throw new RuntimeException("Praktikumsantrag für die Matrikelnummer: " + matrikelnummer +
                    " ist nicht vorhanden und kann nicht gelöscht werden");
        }
        praktikumsantragRepository.deleteById(praktikumsantragDB.get()
                .getAntragsID());
    }

    public void antragUebermitteln(Praktikumsantrag antrag) {
        // Validierung des Start- und Enddatums
        if (antrag.getStartdatum()
                .isAfter(antrag.getEnddatum())) {
            throw new IllegalArgumentException("Das Startdatum darf nicht nach dem Enddatum liegen.");
        }

        // Überprüfen, ob Antrag existiert
        Optional<Praktikumsantrag> existingAntrag = praktikumsantragRepository.findByMatrikelnummer(antrag.getMatrikelnummer());

        if (existingAntrag.isPresent()) {

            // Antrag existiert: aktualisiere den Status
            Praktikumsantrag dbAntrag = existingAntrag.get();
            // Prüfen, ob der Antrag bereits eingereicht wurde
            if (dbAntrag.getStatusAntrag() != StatusAntrag.GESPEICHERT) {
                throw new IllegalStateException("Ein Antrag kann nur übermittelt werden, wenn er den Status 'GESPEICHERT' hat.");
            }

            dbAntrag.setStatusAntrag(StatusAntrag.EINGEREICHT);
            praktikumsantragRepository.save(dbAntrag); // Speichere aktualisierten Antrag
        } else {
            // Neuer Antrag: Status setzen und speichern
            antrag.setStatusAntrag(StatusAntrag.EINGEREICHT);
            praktikumsantragRepository.save(antrag);
        }

        // PBService: Benachrichtigung senden
        pbService.antragUebermitteln(antrag);
    }


    public List<Praktikumsantrag> getAllAntraege() {

        return praktikumsantragRepository.findAll();
    }


    // aktuakisiert Felder, die im Formular nicht null sind. Hilfmethode für antragSpeichern, um Antrag bearbeiten zu können
    private void updateAntragFields(Praktikumsantrag bestehenderAntrag, Praktikumsantrag neuerAntrag) {
        if (neuerAntrag.getNameStudentin() != null) bestehenderAntrag.setNameStudentin(neuerAntrag.getNameStudentin());
        if (neuerAntrag.getVornameStudentin() != null)
            bestehenderAntrag.setVornameStudentin(neuerAntrag.getVornameStudentin());
        if (neuerAntrag.getGebDatumStudentin() != null)
            bestehenderAntrag.setGebDatumStudentin(neuerAntrag.getGebDatumStudentin());
        if (neuerAntrag.getAdresseStudentin() != null)
            bestehenderAntrag.setAdresseStudentin(neuerAntrag.getAdresseStudentin());
        if (neuerAntrag.getPlzStudentin() != null) bestehenderAntrag.setPlzStudentin(neuerAntrag.getPlzStudentin());
        if (neuerAntrag.getOrtStudentin() != null) bestehenderAntrag.setOrtStudentin(neuerAntrag.getOrtStudentin());
        if (neuerAntrag.getTelefonnummerStudentin() != null)
            bestehenderAntrag.setTelefonnummerStudentin(neuerAntrag.getTelefonnummerStudentin());
        if (neuerAntrag.getEmailStudentin() != null)
            bestehenderAntrag.setEmailStudentin(neuerAntrag.getEmailStudentin());
        if (neuerAntrag.getVorschlagPraktikumsbetreuerIn() != null)
            bestehenderAntrag.setVorschlagPraktikumsbetreuerIn(neuerAntrag.getVorschlagPraktikumsbetreuerIn());
        if (neuerAntrag.getPraktikumssemester() != null)
            bestehenderAntrag.setPraktikumssemester(neuerAntrag.getPraktikumssemester());
        if (neuerAntrag.getStudiensemester() != null)
            bestehenderAntrag.setStudiensemester(neuerAntrag.getStudiensemester());
        if (neuerAntrag.getStudiengang() != null) bestehenderAntrag.setStudiengang(neuerAntrag.getStudiengang());
        if (neuerAntrag.getDatumAntrag() != null) bestehenderAntrag.setDatumAntrag(neuerAntrag.getDatumAntrag());
        if (neuerAntrag.getAuslandspraktikum() != null) bestehenderAntrag.setAuslandspraktikum(neuerAntrag.getAuslandspraktikum());
        if (neuerAntrag.getNamePraktikumsstelle() != null)
            bestehenderAntrag.setNamePraktikumsstelle(neuerAntrag.getNamePraktikumsstelle());
        if (neuerAntrag.getStrassePraktikumsstelle() != null)
            bestehenderAntrag.setStrassePraktikumsstelle(neuerAntrag.getStrassePraktikumsstelle());
        if (neuerAntrag.getPlzPraktikumsstelle() != null)
            bestehenderAntrag.setPlzPraktikumsstelle(neuerAntrag.getPlzPraktikumsstelle());
        if (neuerAntrag.getOrtPraktikumsstelle() != null)
            bestehenderAntrag.setOrtPraktikumsstelle(neuerAntrag.getOrtPraktikumsstelle());
        if(neuerAntrag.getBundesland() != null)
            bestehenderAntrag.setBundesland(neuerAntrag.getBundesland());
        if (neuerAntrag.getLandPraktikumsstelle() != null)
            bestehenderAntrag.setLandPraktikumsstelle(neuerAntrag.getLandPraktikumsstelle());
        if (neuerAntrag.getAnsprechpartnerPraktikumsstelle() != null)
            bestehenderAntrag.setAnsprechpartnerPraktikumsstelle(neuerAntrag.getAnsprechpartnerPraktikumsstelle());
        if (neuerAntrag.getTelefonPraktikumsstelle() != null)
            bestehenderAntrag.setTelefonPraktikumsstelle(neuerAntrag.getTelefonPraktikumsstelle());
        if (neuerAntrag.getEmailPraktikumsstelle() != null)
            bestehenderAntrag.setEmailPraktikumsstelle(neuerAntrag.getEmailPraktikumsstelle());
        if (neuerAntrag.getAbteilung() != null) bestehenderAntrag.setAbteilung(neuerAntrag.getAbteilung());
        if (neuerAntrag.getTaetigkeit() != null) bestehenderAntrag.setTaetigkeit(neuerAntrag.getTaetigkeit());
        if (neuerAntrag.getStartdatum() != null) bestehenderAntrag.setStartdatum(neuerAntrag.getStartdatum());
        if (neuerAntrag.getEnddatum() != null) bestehenderAntrag.setEnddatum(neuerAntrag.getEnddatum());

    }

    //methoden zur berechnung der Arbeitstage

    public int berechneArbeitstage(String bundesland, LocalDate startDate, LocalDate endDate, Arbeitswoche arbeitswoche) {
        return switch (arbeitswoche) {
            case VIERTAGEWOCHE -> ArbeitstageRechner.berechneArbeitstageMitVierTageWoche(startDate, endDate);
            case FUENFTAGEWOCHE ->
                    ArbeitstageRechner.berechneArbeitstageMitFuenfTageWoche(startDate, endDate, bundesland);
            default -> throw new IllegalArgumentException("Fehler in der Bestimmung der" + arbeitswoche);
        };
    }

}

/*
 Die Service-Schicht übernimmt hier die zentrale Logik und kümmert sich um  die Verwaltung der Praktikumsanträge.
Stellt Endpunkte für CRUD-Operationen bereit, damit das Frontend die Daten über HTTP  abrufen und verwalten kann.
REST-Methoden: `POST`, `GET`, `PUT`, `DELETE`
Diese Struktur hält die Geschäftslogik zentral und gut organisiert, sodass das Vaadin-Frontend, später problemlos mit den Anträgen arbeiten kann.
Service-Schicht verwaltet die zentrale Geschäftslogik, macht den Code wiederverwendbar und erleichtert Wartung und Erweiterung.
 */





