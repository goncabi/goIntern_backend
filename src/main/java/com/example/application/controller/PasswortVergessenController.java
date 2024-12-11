package com.example.application.controller;

import com.example.application.models.Sicherheitsfrage;
import com.example.application.services.PasswortVergessenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class PasswortVergessenController {
    private final PasswortVergessenService passwortVergessenService;

    @PostMapping("/passwort-vergessen")
    public ResponseEntity<String> eingabeMatrikelnummer(@RequestBody String matrikelnummer) {
        try{
            if(passwortVergessenService.eingabeMatrikelnummer(matrikelnummer)){
                return ResponseEntity.ok("Eingabe korrekt.");
            }
            else{
                return ResponseEntity.badRequest().body("User mit Matrikelnummer nicht vorhanden.");
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein unerwarteter Fehler ist aufgetreten.");
        }
    }

    @GetMapping("/passwort-vergessen/{matrikelnummer}")
    public ResponseEntity<Sicherheitsfrage> ausgabeSicherheitsfrage(@PathVariable String matrikelnummer) {
        return ResponseEntity.ok(passwortVergessenService.getSicherheitsfrage(matrikelnummer));
    }

    @PostMapping("/passwort-vergessen/{matrikelnummer}")
    public ResponseEntity<String> eingabeAntwort(@PathVariable String matrikelnummer, @RequestBody String antwort) {
        try{
            if(passwortVergessenService.eingabeAntwort(matrikelnummer, antwort)){
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
