package com.example.application.services.registrierung;

import com.example.application.models.*;
import com.example.application.services.StudentinService;
import com.example.application.models.RegistrierungsAnfrage;
import com.example.application.repositories.SicherheitsantwortRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrierungService {

    private final StudentinService studentinService;
    private final SicherheitsantwortRepository sicherheitsantwortRepository;
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
        Sicherheitsantwort antwort = new Sicherheitsantwort(anfrage.getFrage(), anfrage.getMatrikelnummer(), anfrage.getAntwort());
        sicherheitsantwortRepository.save(antwort);
    }

    public void registrieren(String matrikelnummer, String passwort1, String passwort2) {
        boolean isValidMatrikelnummer = matrikelnummerValidierer.isMatrikelnummerValid(matrikelnummer);
        if (!isValidMatrikelnummer) {
            throw new IllegalStateException("Matrikelnummer invalide.");
        }
        boolean isValidPasswort = passwortValidierer.passwordValidation(passwort1, passwort2);
        if (!isValidPasswort) {
            throw new IllegalStateException("Passwort invalide.");
        }
        studentinService.signUpUser(
                new Studentin(matrikelnummer, passwort1, AppUserRole.USER));
    }
}
