package com.example.application.controller;

import com.example.application.models.Praktikumsantrag;
import com.example.application.services.PraktikumsantragService;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@AllArgsConstructor
@RestController
// wandelt Rückgabewerte von Methoden automatisch in JSON um, damit sie über HTTP verwendet werden können.
@RequestMapping("/api/antrag")//legt die Basis-URL für alle Endpunkte fest.
public class PraktikumsantragController {

    private final PraktikumsantragService praktikumsantragService;
    private static final DateTimeFormatter GERMAN_DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final Logger logger = LoggerFactory.getLogger(PraktikumsantragController.class);

    @PostMapping("/speichern")
    public ResponseEntity<?> speichernAntrag(@RequestBody Praktikumsantrag antrag) {
        try {
            // service anrufen, um antrag zu speichern oder zu aktualisieren
            // Speichert den Antrag und aktualisiert das Objekt
         praktikumsantragService.antragSpeichern(antrag);

            return ResponseEntity.ok("Antrag erfolgreich gespeichert!");



        } catch (IllegalArgumentException e) {
            // Validierungsproblem ergibt ein Bad Request
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            // wenn keine änderungen erlaubt sind, ergibt ein forbidden
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            // unerwartete Fehler behandeln
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein unerwarteter Fehler ist aufgetreten: " + e.getMessage());
        }
    }


    //Daten aus der Datenbank löschen mit DeleteMapping
    @DeleteMapping("/{matrikelnummer}")
    public ResponseEntity<String> antragLoeschen(@PathVariable String matrikelnummer) {
        try {
            praktikumsantragService.antragLoeschen(matrikelnummer);
            return ResponseEntity.ok("Praktikumsantrag wurde erfolgreich gelöscht.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Fehler beim Löschen des Antrags: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ein unerwarteter Fehler ist aufgetreten.");
        }
    }

    @PostMapping("/uebermitteln")
    public ResponseEntity<String> uebermittelnAntrag(@RequestBody Praktikumsantrag antrag) { //mit @Valid werden die Angaben validiert.
        try {
            praktikumsantragService.antragUebermitteln(antrag);
            return ResponseEntity.ok("Antrag erfolgreich übermittelt.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body("Fehler beim Übermitteln des Antrags: " + e.getMessage());
        } catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest()
                    .body("Datenvalidierung fehlgeschlagen: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ein unerwarteter Fehler ist aufgetreten.");
        }
    }

    @GetMapping("/alle")
    public ResponseEntity<List<Praktikumsantrag>> getAlleAntraege() {
        try {
            List<Praktikumsantrag> antraege = praktikumsantragService.getAllAntraege();
            return ResponseEntity.ok(antraege);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //Neuer Endpoint:
    //Matrikelnummer wird uebergeben und ein Antrag bekommen
    @GetMapping("getantrag/{matrikelnummer}")
    public ResponseEntity<Praktikumsantrag> getAntrag(@PathVariable String matrikelnummer) {
        try {
            Praktikumsantrag antrag = praktikumsantragService.antragAnzeigen(matrikelnummer);
            return ResponseEntity.ok(antrag);
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/berechnen")
    public ResponseEntity<Integer> berechneArbeitstage(@RequestParam String bundesland, @RequestParam String startDatum,
                                                       @RequestParam String endDatum) {
        logger.info("Berechnung der Arbeitstage gestartet. Bundesland: {}, Startdatum: {}, Enddatum: {}", bundesland, startDatum, endDatum);
        try {
            LocalDate start = LocalDate.parse(startDatum, GERMAN_DATE_FORMAT);
            LocalDate end = LocalDate.parse(endDatum, GERMAN_DATE_FORMAT);

            int arbeitstage = praktikumsantragService.berechneArbeitstage(bundesland, start, end);
            logger.info("Arbeitstage berechnet: {}", arbeitstage);
            return ResponseEntity.ok(arbeitstage);
        }
        catch (DateTimeParseException e) {
            // Fehler bei der Datumskonvertierung
            logger.error("Fehler bei der Datumskonvertierung: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
        catch (Exception e) {
            logger.error("Ein unerwarteter Fehler ist aufgetreten: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/updateStatus/{matrikelnummer}")
    public ResponseEntity<String> updateStatus(@PathVariable String matrikelnummer) {
        try {
            praktikumsantragService.statusUpdateImPraktikumOderAbsolviert(matrikelnummer);
            return ResponseEntity.ok("Status aktualisiert.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

