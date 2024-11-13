package com.example.application.controller;

import com.example.application.models.PasswortVergessenAnfrage;
import com.example.application.services.PasswortVergessenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class PasswortVergessenController {
    private final PasswortVergessenService passwortVergessenService;

    @PostMapping("/passwort-vergessen")
    public String passwortVergessen(@RequestBody PasswortVergessenAnfrage anfrage) {
        if(passwortVergessenService.passwortVergessen(anfrage)) {
            return "Antworten korrekt.";
            //Hier dann eventuell Ausgeben des vergessenen Passworts (neues Passwort erstellen komplizierter) und auf Startseite leiten
            //return "Antworten korrekt. Vergessenes Passwort: " +
            //   studentinRepository.findByMatrikelnummer(loginAnfrage.getMatrikelnummer()).get().getPasswort().toString();
        }
        return "Antworten falsch.";
    }
}
