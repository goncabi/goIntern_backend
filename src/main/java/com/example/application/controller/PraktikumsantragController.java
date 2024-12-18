package com.example.application.controller;

import com.example.application.models.Praktikumsantrag;
import com.example.application.services.PraktikumsantragService;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
// wandelt Rückgabewerte von Methoden automatisch in JSON um, damit sie über HTTP verwendet werden können.
@RequestMapping("/api/antrag")//legt die Basis-URL für alle Endpunkte fest.
public class PraktikumsantragController {

    private final PraktikumsantragService praktikumsantragService;

    @PostMapping("/speichern")
    public ResponseEntity<?> speichernAntrag(@RequestBody Praktikumsantrag antrag) {
        try {
            // service anrufen, um antrag zu speichern oder zu aktualisieren
            praktikumsantragService.antragSpeichern(antrag);

            // antwort erfolgreich
            return ResponseEntity.ok()
                                 .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                 .body(antrag);

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
        List<Praktikumsantrag> antraege = praktikumsantragService.getAllAntraege();
        return ResponseEntity.ok(antraege);
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


}

