package com.example.application.controller;

import com.example.application.models.PasswortVergessenAnfrage;
import com.example.application.services.PasswortVergessenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor
public class PasswortVergessenController {
    private final PasswortVergessenService passwortVergessenService;

    public String passwortVergessen(PasswortVergessenAnfrage anfrage) {
        if(passwortVergessenService.passwortVergessen(anfrage)) {
            return "Antworten korrekt.";
            //Hier dann eventuell Ausgeben des vergessenen Passworts (neues Passwort erstellen komplizierter) und auf Startseite leiten
            //return "Antworten korrekt. Vergessenes Passwort: " +
            //   studentinRepository.findByMatrikelnummer(loginAnfrage.getMatrikelnummer()).get().getPasswort().toString();
        }
        return "Antworten falsch.";
    }
}
