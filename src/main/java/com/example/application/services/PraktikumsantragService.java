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
    public void antragSpeichern(Praktikumsantrag antrag) {
        if (antrag.getMatrikelnummer() == null || antrag.getMatrikelnummer().isBlank()) {
            throw new IllegalArgumentException("Die Matrikelnummer darf nicht leer sein.");
        }

        Optional<Praktikumsantrag> vorhandenerAntrag = praktikumsantragRepository.findByMatrikelnummer(antrag.getMatrikelnummer());

        if (vorhandenerAntrag.isPresent()) {
            // Aktualisiere den existierenden Antrag
            Praktikumsantrag bestehenderAntrag = vorhandenerAntrag.get();
            // Prüfung: Antrag mit Status EINGEREICHT darf nicht mehr geändert werden
            if (bestehenderAntrag.getStatusAntrag() != null || bestehenderAntrag.getStatusAntrag() !=  StatusAntrag.GESPEICHERT) {
                throw new IllegalStateException("Ein eingereichter Antrag kann nicht mehr geändert oder gespeichert werden.");
            }
            // Übernehme alle Felder
            bestehenderAntrag.setNameStudentin(antrag.getNameStudentin());
            bestehenderAntrag.setVornameStudentin(antrag.getVornameStudentin());
            bestehenderAntrag.setGebDatumStudentin(antrag.getGebDatumStudentin());
            bestehenderAntrag.setStrasseStudentin(antrag.getStrasseStudentin());
            bestehenderAntrag.setHausnummerStudentin(antrag.getHausnummerStudentin());
            bestehenderAntrag.setPlzStudentin(antrag.getPlzStudentin());
            bestehenderAntrag.setOrtStudentin(antrag.getOrtStudentin());
            bestehenderAntrag.setTelefonnummerStudentin(antrag.getTelefonnummerStudentin());
            bestehenderAntrag.setEmailStudentin(antrag.getEmailStudentin());
            bestehenderAntrag.setVorschlagPraktikumsbetreuerIn(antrag.getVorschlagPraktikumsbetreuerIn());
            bestehenderAntrag.setPraktikumssemester(antrag.getPraktikumssemester());
            bestehenderAntrag.setStudiensemester(antrag.getStudiensemester());
            bestehenderAntrag.setStudiengang(antrag.getStudiengang());
            bestehenderAntrag.setVoraussetzendeLeistungsnachweise(antrag.getVoraussetzendeLeistungsnachweise());
            bestehenderAntrag.setFehlendeLeistungsnachweise(antrag.getFehlendeLeistungsnachweise());
            bestehenderAntrag.setAusnahmeZulassung(antrag.getAusnahmeZulassung());
            bestehenderAntrag.setDatumAntrag(antrag.getDatumAntrag());
            bestehenderAntrag.setNamePraktikumsstelle(antrag.getNamePraktikumsstelle());
            bestehenderAntrag.setStrassePraktikumsstelle(antrag.getStrassePraktikumsstelle());
            bestehenderAntrag.setPlzPraktikumsstelle(antrag.getPlzPraktikumsstelle());
            bestehenderAntrag.setOrtPraktikumsstelle(antrag.getOrtPraktikumsstelle());
            bestehenderAntrag.setLandPraktikumsstelle(antrag.getLandPraktikumsstelle());
            bestehenderAntrag.setAnsprechpartnerPraktikumsstelle(antrag.getAnsprechpartnerPraktikumsstelle());
            bestehenderAntrag.setTelefonPraktikumsstelle(antrag.getTelefonPraktikumsstelle());
            bestehenderAntrag.setEmailPraktikumsstelle(antrag.getEmailPraktikumsstelle());
            bestehenderAntrag.setAbteilung(antrag.getAbteilung());
            bestehenderAntrag.setTaetigkeit(antrag.getTaetigkeit());
            bestehenderAntrag.setStartdatum(antrag.getStartdatum());
            bestehenderAntrag.setEnddatum(antrag.getEnddatum());
            bestehenderAntrag.setStatusAntrag(antrag.getStatusAntrag());
            bestehenderAntrag.setBegleitendeLehrveranstaltungen(antrag.getBegleitendeLehrveranstaltungen());
            bestehenderAntrag.setVoraussetzendeLeistungsnachweise(antrag.getVoraussetzendeLeistungsnachweise());

            // Speichere aktualisierten Antrag
            praktikumsantragRepository.save(bestehenderAntrag);
        } else {
            // Neuer Antrag: Status setzen und speichern
            antrag.setStatusAntrag(StatusAntrag.GESPEICHERT);
            praktikumsantragRepository.save(antrag);
        }
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

            // hier Werte beispielhaft aktualisieren, wird später mit der Binder-Klasse und Vaadin zusammen geführt
            bearbeiteterAntrag.setAusnahmeZulassung(true);
            bearbeiteterAntrag.setStartdatum(antragVorBearbeitung.getStartdatum());
            bearbeiteterAntrag.setEnddatum(antragVorBearbeitung.getEnddatum());

            // Start- und Enddatum prüfen (falls bearbeitet)
            if (antragVorBearbeitung.getStartdatum() != null && antragVorBearbeitung.getEnddatum() != null) {
                if (antragVorBearbeitung.getEnddatum().isBefore(antragVorBearbeitung.getStartdatum())) {
                    throw new IllegalArgumentException("Das Enddatum darf nicht vor dem Startdatum liegen.");
                }
            }

            // Speichert den aktualisierten Antrag in der Datenbank
            praktikumsantragRepository.save(bearbeiteterAntrag);
            bearbeiteterAntrag.setStatusAntrag(StatusAntrag.GESPEICHERT);
            return "Antrag erfolgreich bearbeitet.";
        }
        else {
            return "Keine weitere Bearbeitung möglich, da Antrag bereits abgesendet";
        }
    }

    public void antragLoeschen(String matrikelnummer) {
        Optional<Praktikumsantrag> praktikumsantragDB = praktikumsantragRepository.findByMatrikelnummer(matrikelnummer);// Es wird aus der Datenbank der Praktikumsantrag mit der ID <id> geholt
        if (praktikumsantragDB.isEmpty()) {
            throw new RuntimeException("Praktikumsantrag für die Matrikelnummer: " + matrikelnummer + " ist nicht vorhanden und kann nicht gelöscht werden");
        }
        praktikumsantragRepository.deleteById(praktikumsantragDB.get().getAntragsID());
    }

    public void antragUebermitteln(Praktikumsantrag antrag) {
        // Validierung des Start- und Enddatums
        if (antrag.getStartdatum().isAfter(antrag.getEnddatum())) {
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
}


/*
 Die Service-Schicht übernimmt hier die zentrale Logik und kümmert sich um  die Verwaltung der Praktikumsanträge.
Stellt Endpunkte für CRUD-Operationen bereit, damit das Frontend die Daten über HTTP  abrufen und verwalten kann.
REST-Methoden: `POST`, `GET`, `PUT`, `DELETE`
Diese Struktur hält die Geschäftslogik zentral und gut organisiert, sodass das Vaadin-Frontend, später problemlos mit den Anträgen arbeiten kann.
Service-Schicht verwaltet die zentrale Geschäftslogik, macht den Code wiederverwendbar und erleichtert Wartung und Erweiterung.
 */





