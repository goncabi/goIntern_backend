package com.example.application.controller;

import com.example.application.models.Praktikumsantrag;
import com.example.application.models.StatusAntrag;
import com.example.application.services.PBService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller für die Verwaltung der Praktikumsanträge durch Praktikumsbeauftragte.
 * <p>Diese Klasse stellt REST-API-Endpunkte bereit, um Praktikumsanträge zu genehmigen, abzulehnen oder zu übermitteln. Sie dient als Schnittstelle für die Praktikumsbeauftragten und ermöglicht die Interaktion mit dem Frontend.</p>
 *
 * <h2>Verfügbare Endpunkte</h2>
 * <ul>
 *     <li><b>POST /pb/antrag/genehmigen:</b> Genehmigt einen Praktikumsantrag</li>
 *     <li><b>POST /pb/antrag/ablehnen/{matrikelnummer}:</b> Lehnt einen Antrag ab und fügt einen Kommentar hinzu.</li>
 *     <li><b>POST /pb/antrag/übermitteln:</b> Übermittelt einen Praktikumsantrag.</li>
 *</ul>
 *
 * <h2>Fehlermeldung</h2>
 * <ul>
 *     <li><b>400 BAD REQUEST:</b> Fehlerhafte Eingaben oder ungültige Parameter.</li>
 *     <li><b>500 INTERNAL SERVER ERROR:</b> Ein unerwarteter Fehler ist aufgetreten.</li>
 * </ul>
 *
 * <h2> Abhängigkeiten:</h2>
 * <ul>
 *     <li>{@link PBService}: Geschäftslogik für die Verarbeitung von Praktikumsanträgen.</li>
 * </ul>
 */

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

    @PostMapping("/antrag/ablehnen/{matrikelnummer}") //das ist der Endpunkt der im Frontend verwendet wird. In der Klasse Praktikumsbeauftragter in der ablehnenAntragMitKommentar Methode.
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

}
