package com.example.application.controller;

import com.example.application.models.Praktikumsantrag;
import com.example.application.services.PBService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pb")
@AllArgsConstructor
public class PBController {

    private final PBService pbService;

    @PostMapping("/antrag/genehmigen")
    public ResponseEntity<String> genehmigenAntrag(@RequestBody Praktikumsantrag antrag) {
        try {
            pbService.antragGenehmigen(antrag);
            return ResponseEntity.ok("Der Antrag wurde erfolgreich genehmigt.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler beim Genehmigen des Antrags: " + e.getMessage());
        }
    }

    @PostMapping("/antrag/ablehnen")
    public ResponseEntity<String> ablehnenAntrag(@RequestBody Praktikumsantrag antrag) {
        try {
            pbService.antragAblehnen(antrag);
            return ResponseEntity.ok("Der Antrag wurde erfolgreich abgelehnt.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler beim Ablehnen des Antrags: " + e.getMessage());
        }
    }

    @PostMapping("/antrag/uebermitteln")
    public ResponseEntity<String> uebermittelnAntrag(@RequestBody Praktikumsantrag antrag) {
        try {
            pbService.antragUebermitteln(antrag);
            return ResponseEntity.ok("Der Antrag wurde erfolgreich übermittelt.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Fehler beim Übermitteln des Antrags: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein unerwarteter Fehler ist aufgetreten.");
        }
    }
}
