package com.example.application.services;

import com.example.application.models.Praktikumsantrag;
import com.example.application.models.StatusAntrag;
import com.example.application.repositories.PraktikumsantragRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

/**
 * Service-Schicht zur Verwaltung von Praktikumsanträgen.
 * <p>
 * Diese Klasse enthält die zentrale Geschäftslogik für die Bearbeitung und Verwaltung
 * von Praktikumsanträgen. Sie ermöglicht das Speichern, Aktualisieren, Übermitteln,
 * Löschen und Abrufen von Anträgen sowie die Aktualisierung des Antragsstatus.
 * </p>
 * <h2>Hauptfunktionen</h2>
 * <ul>
 *   <li>Speichern oder Aktualisieren von Anträgen, einschließlich Versionierung.</li>
 *   <li>Löschen von Anträgen basierend auf der Matrikelnummer.</li>
 *   <li>Übermitteln von Anträgen mit Validierung von Zeiträumen.</li>
 *   <li>Aktualisieren des Antragsstatus basierend auf Start- und Enddatum.</li>
 *   <li>Abrufen aller gespeicherten Anträge aus der Datenbank.</li>
 * </ul>
 * <h2>Abhängigkeiten</h2>
 * <ul>
 *   <li>{@link PraktikumsantragRepository}: Schnittstelle für den Datenbankzugriff.</li>
 *   <li>{@link PBService}: Service für Benachrichtigungen und Validierungen.</li>
 * </ul>
 */
@Validated
@AllArgsConstructor
@Service

public class PraktikumsantragService {

    //Objektvariablen:
    private final PraktikumsantragRepository praktikumsantragRepository;
    private final PBService pbService;
    private final BenachrichtigungService benachrichtigungService;

    /**
     * Speichert oder aktualisiert einen Praktikumsantrag.
     * <p>
     * Wenn ein Antrag mit derselben Matrikelnummer existiert, wird er aktualisiert.
     * Abgelehnte Anträge werden versioniert. Neue Anträge werden erstellt und gespeichert.
     * </p>
     * @param antrag Der zu speichernde oder zu aktualisierende Antrag.
     * @throws IllegalArgumentException Falls die Matrikelnummer fehlt oder leer ist.
     */
    public void antragSpeichern(Praktikumsantrag antrag) {
        if (antrag.getMatrikelnummer() == null || antrag.getMatrikelnummer()
                .isBlank()) {
            throw new IllegalArgumentException("Die Matrikelnummer darf nicht leer sein.");
        }

        Optional<Praktikumsantrag> vorhandenerAntrag = praktikumsantragRepository.findByMatrikelnummer(antrag.getMatrikelnummer());

        if (vorhandenerAntrag.isPresent()) {
            Praktikumsantrag bestehenderAntrag = vorhandenerAntrag.get();
            // Prüfen, ob der Antrag bereits abgelehnt wurde - Version wird erhöht
            if (bestehenderAntrag.getStatusAntrag() == StatusAntrag.ABGELEHNT) {
                bestehenderAntrag.setAntragsVersion(bestehenderAntrag.getAntragsVersion() + 1);
            }
            // Felder aktualisieren
            updateAntragFields(bestehenderAntrag,
                    antrag);
            bestehenderAntrag.setStatusAntrag(StatusAntrag.GESPEICHERT);

            praktikumsantragRepository.save(bestehenderAntrag);
        } else {
            // Neuer Antrag speichern
            antrag.setStatusAntrag(StatusAntrag.GESPEICHERT);
            praktikumsantragRepository.save(antrag);
        }
    }

    /**
     * Löscht einen Praktikumsantrag und falls vorhanden deren zugehörige Kommentare basierend auf der Matrikelnummer.
     * <p>
     * Wenn der Antrag existiert, wird er gelöscht. Wenn zu der Matrikelnummer zugehörige Kommentare vorhanden sind, werden diese ebenfalls gelöscht.
     * Zusätzlich wird eine Benachrichtigung über den {@link PBService} gesendet, dass der Antrag zurückgezogen wurde,
     * falls der Antragsstatus 'Zugelassen' ist.
     * </p>
     * @param matrikelnummer Die Matrikelnummer des zu löschenden Antrags.
     * @throws RuntimeException Falls kein Antrag mit der angegebenen Matrikelnummer gefunden wurde.
     */
    public void antragLoeschen(String matrikelnummer) {
        Optional<Praktikumsantrag> praktikumsantragDB = praktikumsantragRepository.findByMatrikelnummer(matrikelnummer);// Es wird aus der Datenbank der Praktikumsantrag mit der ID <id> geholt
        if (praktikumsantragDB.isEmpty()) {
            throw new RuntimeException("Praktikumsantrag für die Matrikelnummer: " + matrikelnummer +
                    " ist nicht vorhanden und kann nicht gelöscht werden");
        }
        praktikumsantragRepository.deleteById(praktikumsantragDB.get()
                .getAntragsID());
        benachrichtigungService.unwichtigeNachrichtenLoeschen(matrikelnummer);
        if(praktikumsantragDB.get().getStatusAntrag() == StatusAntrag.ZUGELASSEN){
            pbService.antragZurueckgezogen(matrikelnummer);
        }
    }

    /**
     * Übermittelt (speichert) einen Praktikumsantrag nach Validierung.
     * <p>
     * Überprüft, ob Start- und Enddatum korrekt sind, und setzt den Status des Antrags
     * auf "Eingereicht". Wenn der Antrag bereits existiert, wird er aktualisiert. Diese Methode
     * ruft zudem die Methode {@code antragUebermitteln} von {@link PBService} auf, um weitere
     * Benachrichtigungen zu senden.
     * </p>
     * @param antrag Der zu übermittelnde Antrag.
     * @throws IllegalArgumentException Wenn das Startdatum nach dem Enddatum liegt.
     */
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
            // Prüfen, ob der Antrag bereits abgelehnt wurde - Version wird erhöht
            if (dbAntrag.getStatusAntrag() == StatusAntrag.ABGELEHNT) {
                dbAntrag.setAntragsVersion(dbAntrag.getAntragsVersion() + 1);
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

    /**
     * Gibt alle gespeicherten Praktikumsanträge zurück.
     * @return Eine Liste aller Anträge in der Datenbank.
     */
    public List<Praktikumsantrag> getAllAntraege() {
        return praktikumsantragRepository.findAll();
    }

    /**
     * Hilfsmethode zur Aktualisierung der Felder eines bestehenden Antrags.
     * <p>
     * Aktualisiert nur die Felder, die im neuen Antrag nicht null sind.
     * </p>
     * @param bestehenderAntrag Der bestehende Antrag, der aktualisiert werden soll.
     * @param neuerAntrag Der neue Antrag mit den zu übernehmenden Werten.
     */
    private void updateAntragFields(Praktikumsantrag bestehenderAntrag, Praktikumsantrag neuerAntrag) {
        if (neuerAntrag.getNameStudentin() != null) bestehenderAntrag.setNameStudentin(neuerAntrag.getNameStudentin());
        if (neuerAntrag.getVornameStudentin() != null)
            bestehenderAntrag.setVornameStudentin(neuerAntrag.getVornameStudentin());
        if (neuerAntrag.getGebDatumStudentin() != null)
            bestehenderAntrag.setGebDatumStudentin(neuerAntrag.getGebDatumStudentin());
        if (neuerAntrag.getStrasseHausnummerStudentin() != null)
            bestehenderAntrag.setStrasseHausnummerStudentin(neuerAntrag.getStrasseHausnummerStudentin());
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
        if (neuerAntrag.getAuslandspraktikum() != null)
            bestehenderAntrag.setAuslandspraktikum(neuerAntrag.getAuslandspraktikum());
        if (neuerAntrag.getNamePraktikumsstelle() != null)
            bestehenderAntrag.setNamePraktikumsstelle(neuerAntrag.getNamePraktikumsstelle());
        if (neuerAntrag.getStrassePraktikumsstelle() != null)
            bestehenderAntrag.setStrassePraktikumsstelle(neuerAntrag.getStrassePraktikumsstelle());
        if (neuerAntrag.getPlzPraktikumsstelle() != null)
            bestehenderAntrag.setPlzPraktikumsstelle(neuerAntrag.getPlzPraktikumsstelle());
        if (neuerAntrag.getOrtPraktikumsstelle() != null)
            bestehenderAntrag.setOrtPraktikumsstelle(neuerAntrag.getOrtPraktikumsstelle());
        if (neuerAntrag.getBundeslandPraktikumsstelle() != null)
            bestehenderAntrag.setBundeslandPraktikumsstelle(neuerAntrag.getBundeslandPraktikumsstelle());
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

   /* public void updateStatusZuAbgebrochen(String matrikelnummer) {
        Optional <Praktikumsantrag>  antrag = praktikumsantragRepository.findByMatrikelnummer(matrikelnummer);
        if(antrag.isPresent()) {
            Praktikumsantrag praktikumsantrag = antrag.get();
            praktikumsantrag.setStatusAntrag(StatusAntrag.ABGEBROCHEN);
            praktikumsantragRepository.save(praktikumsantrag);
        }
        else {
            throw new RuntimeException("Fehler beim Aufrufen des Antrags.");
        }
    }*/


    /**
     * Aktualisiert den Status eines Praktikumsantrags basierend auf dem aktuellen Datum.
     * <p>
     * Diese Methode überprüft, ob der Antrag den Status "Zugelassen" hat und ob die Start- und Enddaten
     * korrekt gesetzt sind. Basierend auf dem aktuellen Datum wird der Status des Antrags wie folgt aktualisiert:
     * <ul>
     *   <li><b>Im Praktikum:</b> Wenn das aktuelle Datum innerhalb des Praktikumszeitraums liegt.</li>
     *   <li><b>Absolviert:</b> Wenn das aktuelle Datum nach dem Praktikumsenddatum liegt.</li>
     * </ul>
     * </p>
     * <p>
     * Wenn keine passenden Start- oder Enddaten vorhanden sind, wird der Status nicht geändert.
     * </p>
     * Wenn der Status auf 'Absolviert' geändert wurde, werden, falls vorhanden, die Nachrichten an den PB und die Studentin
     * über die bereits absolvierten Arbeitstage der Studentin gelöscht.
     *
     * @param matrikelnummer Die Matrikelnummer des Antrags, dessen Status aktualisiert werden soll.
     * @throws IllegalArgumentException Wenn kein Antrag mit der angegebenen Matrikelnummer gefunden wurde.
     */
    public void updateAntragStatus(String matrikelnummer) {
        Optional<Praktikumsantrag> antragOpt = praktikumsantragRepository.findByMatrikelnummer(matrikelnummer);
        if (antragOpt.isPresent()) {
            Praktikumsantrag antrag = antragOpt.get();
            LocalDate today = LocalDate.now();
            if (antrag.getStatusAntrag() == StatusAntrag.ZUGELASSEN) {
                if (antrag.getStartdatum() != null && antrag.getEnddatum() != null) {
                    if (!today.isBefore(antrag.getStartdatum()) && !today.isAfter(antrag.getEnddatum())) {
                        antrag.setStatusAntrag(StatusAntrag.IMPRAKTIKUM);
                    } else if (today.isAfter(antrag.getEnddatum())) {
                        antrag.setStatusAntrag(StatusAntrag.ABSOLVIERT);
                        //Arbeitstagenachricht wird bei absolviert bei Studentin und PB gelöscht
                        benachrichtigungService.arbeitstagenachrichtenLoeschen(antrag.getMatrikelnummer());
                    }
                    praktikumsantragRepository.save(antrag);
                }
            }
        } else {
            throw new RuntimeException("Fehler beim Aufrufen des Antrags.");
        }
    }

    public void updateStatus(String matrikelnummer, StatusAntrag neuerStatus) {
        Optional<Praktikumsantrag> antrag = praktikumsantragRepository.findByMatrikelnummer(matrikelnummer);
        if (antrag.isPresent()) {
            Praktikumsantrag praktikumsantrag = antrag.get();
            praktikumsantrag.setStatusAntrag(neuerStatus);
            praktikumsantragRepository.save(praktikumsantrag);
        } else {
            throw new RuntimeException("Fehler beim Aufrufen des Antrags.");
        }
    }

}


/*
 Die Service-Schicht übernimmt hier die zentrale Logik und kümmert sich um  die Verwaltung der Praktikumsanträge.
Stellt Endpunkte für CRUD-Operationen bereit, damit das Frontend die Daten über HTTP  abrufen und verwalten kann.
REST-Methoden: `POST`, `GET`, `PUT`, `DELETE`
Diese Struktur hält die Geschäftslogik zentral und gut organisiert, sodass das Vaadin-Frontend, später problemlos mit den Anträgen arbeiten kann.
Service-Schicht verwaltet die zentrale Geschäftslogik, macht den Code wiederverwendbar und erleichtert Wartung und Erweiterung.
 */





