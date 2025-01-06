package com.example.application.controller;

import com.example.application.models.Praktikumsantrag;
import com.example.application.models.StatusAntrag;
import com.example.application.services.PBService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pb")
@AllArgsConstructor
public class PBController {

    private final PBService pbService;

    //diese Methode gibt nur einen aktualisierten Antrag zurück - Endpunkt für Studentin-ANsicht
    @PostMapping("/antrag/genehmigen")
    public ResponseEntity<String> genehmigenAntrag(@RequestBody Praktikumsantrag antrag) {
        try {
            pbService.antragGenehmigen(antrag.getMatrikelnummer());
            return ResponseEntity.ok("Der Antrag wurde erfolgreich genehmigt.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler beim Genehmigen des Antrags: " + e.getMessage());
        }
    }

    @PostMapping("/antrag/ablehnen/{matrikelnummer}")
    public ResponseEntity<String> ablehnenAntrag(@PathVariable String matrikelnummer, @RequestBody String ablehnenNotiz) {
        try {
            pbService.antragAblehnen(matrikelnummer, ablehnenNotiz);
            return ResponseEntity.ok("Der Antrag wurde abgelehnt.");

        }
        catch (Exception e) {
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


    //hier ein Endpunkt, um Status von Eingereicht in In Bearbeitung bei Klick auf Antrag anzeigen zu ändern
//    @PutMapping("/{matrikelnummer}/anzeigen")
//    public ResponseEntity<String> setInBearbeitung(@PathVariable String matrikelnummer) {
//        try {
//            praktikumsantragService.updateStatusToInBearbeitung(matrikelnummer);
//            return ResponseEntity.ok("Status geändert zu 'in Bearbeitung'");
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fehler: Status konnte nicht geändert werden. " + e.getMessage());
//        }
//    }
}
