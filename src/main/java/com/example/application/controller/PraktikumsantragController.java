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

/**
 * Controller für die Verwaltung von Praktikumsanträgen.
 * <p>
 * Diese Klasse bietet HTTP-Endpunkte zur Bearbeitung von Praktikumsanträgen.
 * Sie ermöglicht die Durchführung von CRUD-Operationen (Create, Read, Update, Delete)
 * sowie spezifische Aktionen wie das Übermitteln und Generieren von Mock-Daten.
 * </p>
 *
 * <h2>Verfügbare Endpunkte</h2>
 * <ul>
 *   <li><b>POST /api/antrag/speichern:</b> Speichert oder aktualisiert einen Praktikumsantrag.</li>
 *   <li><b>DELETE /api/antrag/{matrikelnummer}:</b> Löscht einen Antrag basierend auf der Matrikelnummer.</li>
 *   <li><b>POST /api/antrag/uebermitteln:</b> Übermittelt einen Antrag nach Validierung.</li>
 *   <li><b>GET /api/antrag/alle:</b> Ruft alle Praktikumsanträge ab.</li>
 *   <li><b>GET /api/antrag/getantrag/{matrikelnummer}:</b> Ruft einen Antrag basierend auf der Matrikelnummer ab.</li>
 *   <li><b>PUT /api/antrag/updateStatus/{matrikelnummer}:</b> Aktualisiert den Status eines Antrags.</li>
 *   <li><b>DELETE /api/antrag/clear:</b> Löscht alle Anträge aus der Datenbank.</li>
 *   <li><b>POST /api/antrag/generate:</b> Generiert Mock-Daten für Praktikumsanträge.</li>
 * </ul>
 *
 * <h2>Fehlerbehandlung</h2>
 * <ul>
 *   <li><b>400 BAD REQUEST:</b> Fehlerhafte Eingabe oder Validierungsfehler.</li>
 *   <li><b>403 FORBIDDEN:</b> Aktion ist nicht erlaubt.</li>
 *   <li><b>404 NOT FOUND:</b> Der angeforderte Antrag wurde nicht gefunden.</li>
 *   <li><b>500 INTERNAL SERVER ERROR:</b> Ein unerwarteter Fehler ist aufgetreten.</li>
 * </ul>
 *
 * <h2>Abhängigkeiten</h2>
 * <ul>
 *   <li>{@link PraktikumsantragService}: Geschäftslogik für die Antragsverwaltung.</li>
 *   <li>{@link PraktikumsantragRepository}: Zugriff auf die Datenbank für Praktikumsanträge.</li>
 *   <li>{@link MockDataService}: Generierung von Testdaten für Anträge.</li>
 * </ul>
 *
 */
@AllArgsConstructor
@RestController
// wandelt Rückgabewerte von Methoden automatisch in JSON um, damit sie über HTTP verwendet werden können.
@RequestMapping("/api/antrag")//legt die Basis-URL für alle Endpunkte fest.

public class PraktikumsantragController {

    private final PraktikumsantragService praktikumsantragService;
    private final PraktikumsantragRepository praktikumsantragRepository;
    @Autowired
    private MockDataService mockDataService;
    /**
     * Speichert oder aktualisiert einen Praktikumsantrag.
     * @param antrag Der zu speichernde oder zu aktualisierende Praktikumsantrag.
     * @return Erfolgs- oder Fehlermeldung als ResponseEntity.
     */
    @PostMapping("/speichern")
    public ResponseEntity<?> speichernAntrag(@RequestBody Praktikumsantrag antrag) {
        try {
            praktikumsantragService.antragSpeichern(antrag);
            return ResponseEntity.ok("Antrag erfolgreich gespeichert!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein unerwarteter Fehler ist aufgetreten: " + e.getMessage());
        }
    }

    /**
     * Löscht einen Antrag basierend auf der Matrikelnummer.
     * @param matrikelnummer Die Matrikelnummer des zu löschenden Antrags.
     * @return Erfolgs- oder Fehlermeldung als ResponseEntity.
     */
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

    /**
     * Übermittelt einen Praktikumsantrag nach Validierung.
     * @param antrag Der zu übermittelnde Praktikumsantrag.
     * @return Erfolgs- oder Fehlermeldung als ResponseEntity.
     */
    @PostMapping("/uebermitteln")
    public ResponseEntity<String> uebermittelnAntrag(@RequestBody Praktikumsantrag antrag) {
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

    /**
     * Ruft alle vorhandenen Praktikumsanträge ab.
     * @return Liste aller Praktikumsanträge als ResponseEntity.
     */
    @GetMapping("/alle")
    public ResponseEntity<List<Praktikumsantrag>> getAlleAntraege() {
        try {
            List<Praktikumsantrag> antraege = praktikumsantragService.getAllAntraege();
            return ResponseEntity.ok(antraege);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Ruft einen Antrag basierend auf der Matrikelnummer ab.
     * @param matrikelnummer Die Matrikelnummer des gewünschten Antrags.
     * @return Der Praktikumsantrag als ResponseEntity oder 404, falls nicht gefunden.
     */
    @GetMapping("/getantrag/{matrikelnummer}")
    public ResponseEntity<Praktikumsantrag> getAntrag(@PathVariable String matrikelnummer) {
        praktikumsantragService.updateAntragStatus(matrikelnummer);
        Optional<Praktikumsantrag> antrag = praktikumsantragRepository.findByMatrikelnummer(matrikelnummer);
        return antrag.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Aktualisiert den Status eines Antrags basierend auf der Matrikelnummer.
     * @param matrikelnummer Die Matrikelnummer des Antrags, dessen Status aktualisiert werden soll.
     * @return Erfolgs- oder Fehlermeldung als ResponseEntity.
     */
    @PutMapping("/updateStatus/{matrikelnummer}")
    public ResponseEntity<String> updateStatus(@PathVariable String matrikelnummer) {
        try {
            praktikumsantragService.statusUpdateImPraktikumOderAbsolviert(matrikelnummer);
            return ResponseEntity.ok("Status aktualisiert.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Löscht alle Anträge aus der Datenbank.
     * @return Erfolgsnachricht.
     */
    @DeleteMapping("/clear")
    public String clearMockData() {
        praktikumsantragRepository.deleteAll();
        return "Alle Anträge wurden erfolgreich gelöscht.";
    }

    /**
     * Generiert eine definierte Anzahl von Mock-Daten für Praktikumsanträge.
     * @param count Die Anzahl der zu generierenden Anträge (Standard: 20).
     * @return Erfolgsnachricht.
     */
    @PostMapping("/generate")
    public String generateMockData(@RequestParam(defaultValue = "20") int count) {
        mockDataService.generateMockData(count);
        return count + " mock Praktikumsanträge wurden erfolgreich generiert und gespeichert.";
    }
}

