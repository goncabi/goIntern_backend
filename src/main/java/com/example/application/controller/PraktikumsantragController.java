package com.example.application.controller;

import com.example.application.models.Praktikumsantrag;
import com.example.application.services.PraktikumsantragService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController // wandelt Rückgabewerte von Methoden automatisch in JSON um, damit sie über HTTP verwendet werden können.
@RequestMapping("/api/antrag")//legt die Basis-URL für alle Endpunkte fest.
public class PraktikumsantragController {

    private final PraktikumsantragService praktikumsantragService;

    @PostMapping("/praktikumsantrag")
    public ResponseEntity<String> PraktikumsantragErstellen(@RequestBody Praktikumsantrag antrag) {
        try {
            praktikumsantragService.antragErstellen(String.valueOf(antrag));
            return ResponseEntity.ok("Praktikumsantrag wurde erfolgreich gespeichert!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Fehler beim Erstellen des Antrags: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein unerwarteter Fehler ist aufgetreten.");
        }
    }

    //Daten aus der Datenbank löschen mit DeleteMapping

    @DeleteMapping("/{id}")
    public ResponseEntity<String> antragLoeschen(@PathVariable Long id) {
        try {
            praktikumsantragService.antragLoeschen(id);
            return ResponseEntity.ok("Praktikumsantrag mit ID: " + id + " wurde erfolgreich gelöscht.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fehler beim Löschen des Antrags: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein unerwarteter Fehler ist aufgetreten.");
        }
    }
    @PostMapping("/uebermitteln/")
    public ResponseEntity<String> uebermittelnAntrag(@Valid @RequestBody Praktikumsantrag antrag) { //mit @Valid werden die Angaben validiert.
        try {
            praktikumsantragService.antragUebermitteln(antrag);
            return ResponseEntity.ok("Antrag erfolgreich übermittelt.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Fehler beim Übermitteln des Antrags: " + e.getMessage());
        } catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest().body("Datenvalidierung fehlgeschlagen: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein unerwarteter Fehler ist aufgetreten.");
        }
    }
    @GetMapping("/alle")
    public ResponseEntity<?> getAlleAntraege() {
        try {
            List<Praktikumsantrag> antraege = praktikumsantragService.getAllAntraege();
            return ResponseEntity.ok(antraege);
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Fehler beim Abrufen der Anträge.");
        }
    }
}



