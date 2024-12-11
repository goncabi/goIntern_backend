package com.example.application.controller;

import com.example.application.models.RegistrierungsAnfrage;
import com.example.application.services.registrierung.RegistrierungService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")//hier kommt die Verlinkung zum Frontend bzw. die http
@AllArgsConstructor
public class RegistrierungController {

    private RegistrierungService registrierungService;

    @PostMapping("/registrieren")
    public ResponseEntity<String> registrieren(@RequestBody RegistrierungsAnfrage anfrage){
        try {
            registrierungService.registrieren(anfrage);
            return ResponseEntity.ok("Registrierung erfolgreich!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Fehler bei der Registrierung: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein unerwarteter Fehler ist aufgetreten.");
        }
    }
}
