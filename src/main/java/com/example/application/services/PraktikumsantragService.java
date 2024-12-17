package com.example.application.services;

import com.example.application.models.Praktikumsantrag;
import com.example.application.models.StatusAntrag;
import com.example.application.repositories.PraktikumsantragRepository;
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
            Praktikumsantrag bestehenderAntrag = vorhandenerAntrag.get();

            // Prüfen, ob der Antrag den Status "GESPEICHERT" hat
            if (bestehenderAntrag.getStatusAntrag() != StatusAntrag.GESPEICHERT) {
                throw new IllegalStateException("Ein eingereichter Antrag kann nicht mehr geändert oder gespeichert werden.");
            }

            // Felder aktualisieren
            updateAntragFields(bestehenderAntrag, antrag);

            praktikumsantragRepository.save(bestehenderAntrag);
        } else {
            // Neuer Antrag speichern
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

    private void updateAntragFields(Praktikumsantrag bestehender, Praktikumsantrag neu) {
        bestehender.setNameStudentin(neu.getNameStudentin());
        bestehender.setVornameStudentin(neu.getVornameStudentin());
        bestehender.setGebDatumStudentin(neu.getGebDatumStudentin());
        bestehender.setStrasseStudentin(neu.getStrasseStudentin());
        bestehender.setHausnummerStudentin(neu.getHausnummerStudentin());
        bestehender.setPlzStudentin(neu.getPlzStudentin());
        bestehender.setOrtStudentin(neu.getOrtStudentin());
        bestehender.setTelefonnummerStudentin(neu.getTelefonnummerStudentin());
        bestehender.setEmailStudentin(neu.getEmailStudentin());
        bestehender.setVorschlagPraktikumsbetreuerIn(neu.getVorschlagPraktikumsbetreuerIn());
        bestehender.setPraktikumssemester(neu.getPraktikumssemester());
        bestehender.setStudiensemester(neu.getStudiensemester());
        bestehender.setStudiengang(neu.getStudiengang());
        bestehender.setVoraussetzendeLeistungsnachweise(neu.getVoraussetzendeLeistungsnachweise());
        bestehender.setFehlendeLeistungsnachweise(neu.getFehlendeLeistungsnachweise());
        bestehender.setAusnahmeZulassung(neu.getAusnahmeZulassung());
        bestehender.setDatumAntrag(neu.getDatumAntrag());
        bestehender.setNamePraktikumsstelle(neu.getNamePraktikumsstelle());
        bestehender.setStrassePraktikumsstelle(neu.getStrassePraktikumsstelle());
        bestehender.setPlzPraktikumsstelle(neu.getPlzPraktikumsstelle());
        bestehender.setOrtPraktikumsstelle(neu.getOrtPraktikumsstelle());
        bestehender.setLandPraktikumsstelle(neu.getLandPraktikumsstelle());
        bestehender.setAnsprechpartnerPraktikumsstelle(neu.getAnsprechpartnerPraktikumsstelle());
        bestehender.setTelefonPraktikumsstelle(neu.getTelefonPraktikumsstelle());
        bestehender.setEmailPraktikumsstelle(neu.getEmailPraktikumsstelle());
        bestehender.setAbteilung(neu.getAbteilung());
        bestehender.setTaetigkeit(neu.getTaetigkeit());
        bestehender.setStartdatum(neu.getStartdatum());
        bestehender.setEnddatum(neu.getEnddatum());
    }

}


/*
 Die Service-Schicht übernimmt hier die zentrale Logik und kümmert sich um  die Verwaltung der Praktikumsanträge.
Stellt Endpunkte für CRUD-Operationen bereit, damit das Frontend die Daten über HTTP  abrufen und verwalten kann.
REST-Methoden: `POST`, `GET`, `PUT`, `DELETE`
Diese Struktur hält die Geschäftslogik zentral und gut organisiert, sodass das Vaadin-Frontend, später problemlos mit den Anträgen arbeiten kann.
Service-Schicht verwaltet die zentrale Geschäftslogik, macht den Code wiederverwendbar und erleichtert Wartung und Erweiterung.
 */





