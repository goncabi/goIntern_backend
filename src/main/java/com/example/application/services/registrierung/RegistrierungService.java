package com.example.application.services.registrierung;

import com.example.application.models.*;
import com.example.application.services.StudentinService;
import com.example.application.models.RegistrierungsAnfrage;
import com.example.application.repositories.SicherheitsantwortRepository;
import com.example.application.repositories.SicherheitsfrageRepository;
import com.example.application.repositories.StudentinRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RegistrierungService {

    private final StudentinService studentinService;
    private final SicherheitsfrageRepository sicherheitsfrageRepository;
    private final SicherheitsantwortRepository sicherheitsantwortRepository;
    private final StudentinRepository studentinRepository;
    private final PasswortValidierer passwortValidierer;
    private MatrikelnummerValidierer matrikelnummerValidierer;

    public void registrieren(RegistrierungsAnfrage anfrage) {
        boolean isValidMatrikelnummer = matrikelnummerValidierer.isMatrikelnummerValid(anfrage.getMatrikelnummer());
        if (!isValidMatrikelnummer) {
            throw new IllegalStateException("Matrikelnummer invalide.");
        }
        boolean isValidPasswort = passwortValidierer.passwordValidation(anfrage.getPasswort1(), anfrage.getPasswort2());
        if (!isValidPasswort) {
            throw new IllegalStateException("Passwort invalide.");
        }
        studentinService.signUpUser(
                new Studentin(anfrage.getMatrikelnummer(), anfrage.getPasswort1(), AppUserRole.USER));
        antwortenSpeichern(anfrage);
    }

    private void antwortenSpeichern(RegistrierungsAnfrage anfrage) {
        Sicherheitsantwort antwort1 = new Sicherheitsantwort(
                sicherheitsfrageRepository.findById(1L).get(),
                studentinRepository.findByMatrikelnummer(anfrage.getMatrikelnummer()).get(),
                anfrage.getAntwort1());
        Sicherheitsantwort antwort2 = new Sicherheitsantwort(
                sicherheitsfrageRepository.findById(2L).get(),
                studentinRepository.findByMatrikelnummer(anfrage.getMatrikelnummer()).get(),
                anfrage.getAntwort2());
        Sicherheitsantwort antwort3 = new Sicherheitsantwort(
                sicherheitsfrageRepository.findById(3L).get(),
                studentinRepository.findByMatrikelnummer(anfrage.getMatrikelnummer()).get(),
                anfrage.getAntwort3());

        sicherheitsantwortRepository.saveAll(List.of(antwort1, antwort2, antwort3));
    }
}
