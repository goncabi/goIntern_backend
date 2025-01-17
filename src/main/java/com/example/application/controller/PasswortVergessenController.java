package com.example.application.controller;

import com.example.application.models.PasswortVergessenAnfrage;
import com.example.application.services.PasswortVergessenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Dieser Controller kümmert sich um das Zurücksetzten von Passwärtern.
 *
 *   <p>Hier können Nutzer ihre Matrikelnummer eingeben, um die Sicherheitsfragen zu erhalten.
 *   Nach der richtigen Beantwortung können sie ihr passwort zurücksetzten.</p>
 *
 *   <h2>Was dieser Controller kann</h2>
 *   <ul>
 *       <li><b>POST /api/auth/passwort-vergessen:</b> Überprüft die Matrikelnummer und liefert die passende Sicherheitsfarge zurück.</li>
 *      <li><b>POST /api/auth/passwort-vergessen/fragen:</b> Überprüft die antworten auch sicherheitsfragen und setzt bei Erfolg das Passwort zurück</li>
 *   </ul>
 *
 *  <h2> Umgang mit Fehlern</h2>
 *   <ul>
 *       <li><b>400 BAD REQUEST:</b> Wenn die eingaben ungültig sind oder der Nutzer nicht gefunden wird</li>
 *       <li><b>500 INTERNAL SERVER ERROR:</b> wenn ein unerwarteter Fehler auftritt</li>
 *   </ul>
 */

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class PasswortVergessenController {
    private final PasswortVergessenService passwortVergessenService;

    /**
     * Überprüft die Matrikelnummer und gibt die Sicherheitsfrage zurück.
     * @param matrikelnummer Die Matrikelnummer des nutzers.
     * @return Die Sicherheitsfragen wenn die Matrikelnummer existiert oder eine Fehlermeldung.
     */

    @PostMapping("/passwort-vergessen")
    public ResponseEntity<String> eingabeMatrikelnummer(@RequestBody String matrikelnummer) {
        try{
            if(passwortVergessenService.eingabeMatrikelnummer(matrikelnummer)){
                return ResponseEntity.ok(passwortVergessenService.getSicherheitsfrage(matrikelnummer));
            }
            else{
                return ResponseEntity.badRequest().body("User mit Matrikelnummer nicht vorhanden.");
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein unerwarteter Fehler ist aufgetreten.");
        }
    }

    /**
     * Überprüft die Antwort auf die Sicherheitsfrage und setzt das Passwort zurück.
     * @param anfrage Die anfrage mit matrikelnummer, antwort und neuem Passwort.
     * @return Eine Bestätigung wenn die Antwort korrekt war, oder eine Fehlermeldung.
     */

    @PostMapping("/passwort-vergessen/frage")
    public ResponseEntity<String> eingabeAntwort(@RequestBody PasswortVergessenAnfrage anfrage) {
        String matrikelnummer = anfrage.getMatrikelnummer();
        String antwort = anfrage.getAntwort();
        String passwort = anfrage.getPasswort();
        try{
            if(passwortVergessenService.resetPassword(matrikelnummer, antwort, passwort)){
                return ResponseEntity.ok("Antwort korrekt.");
            }
            else{
                return ResponseEntity.badRequest().body("Antwort nicht korrekt.");
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein unerwarteter Fehler ist aufgetreten.");
        }
    }

}
