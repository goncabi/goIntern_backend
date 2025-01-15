package com.example.application.controller;


import com.example.application.models.Praktikumsantrag;
import com.example.application.repositories.PraktikumsantragRepository;
import com.example.application.services.MockDataService;
import com.example.application.services.PraktikumsantragService;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
// wandelt Rückgabewerte von Methoden automatisch in JSON um, damit sie über HTTP verwendet werden können.
@RequestMapping("/api/antrag")//legt die Basis-URL für alle Endpunkte fest.

public class PraktikumsantragController {

    private final PraktikumsantragService praktikumsantragService;
    private final PraktikumsantragRepository praktikumsantragRepository;
    @Autowired
    private MockDataService mockDataService;


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
        praktikumsantragService.updateAntragStatus(matrikelnummer);
        Optional<Praktikumsantrag> antrag = praktikumsantragRepository.findByMatrikelnummer(matrikelnummer);
        return antrag.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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

    @DeleteMapping("/clear")
    public String clearMockData() {
            praktikumsantragRepository.deleteAll();
            return "Alle Anträge wurden erfolgreich gelöscht.";
        }
    @PostMapping("/generate")
    public String generateMockData(@RequestParam(defaultValue = "20") int count) {
        mockDataService.generateMockData(count);
        return count + " mock Praktikumsanträge wurden erfolgreich generiert und gespeichert.";
    }
}

