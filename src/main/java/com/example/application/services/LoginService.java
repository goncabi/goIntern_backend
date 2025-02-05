package com.example.application.services;

import com.example.application.models.*;
import com.example.application.repositories.PBRepository;
import com.example.application.repositories.StudentinRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * LoginService verwaltet die Authentifizierung von Benutzern basierend auf ihrer Rolle.
 * <p>
 * Dieser Service ermöglicht die Anmeldung von Benutzern mit unterschiedlichen Rollen,
 * indem er die eingegebenen Anmeldedaten überprüft und validiert.
 * </p>
 *
 * <ul>
 *   <li>Verwendet das {@link StudentinRepository}, um Studentendaten zu überprüfen.</li>
 *   <li>Verwendet das {@link PBRepository}, um Praktikumsbeauftragte zu authentifizieren.</li>
 *   <li>Rückgabe eines eindeutigen Identifikators bei erfolgreicher Authentifizierung.</li>
 * </ul>
 *
 * @author Beyza Nur Acikgöz
 * @version 1.0
 */

@AllArgsConstructor
@Service
public class LoginService {

    /**
     * Repository für die Verwaltung von Studentendaten.
     */
    private final StudentinRepository studentinRepository;

    /**
     * Repository für die Verwaltung der Praktikumsbeauftragten-Daten.
     */
    private final PBRepository praktikumsbeauftragterRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Führt den Login-Vorgang basierend auf der Benutzerrolle durch.
     * <p>
     * Diese Methode entscheidet, ob der Benutzer ein Student oder ein Praktikumsbeauftragter ist
     * und ruft die entsprechende Login-Logik auf.
     * </p>
     *
     * @param loginAnfrage Die Login-Daten, bestehend aus Benutzername, Passwort und Rolle.
     * @return Eine {@link Optional} Matrikelnummer oder Identifikator, wenn die Authentifizierung erfolgreich war.
     */
    public Optional<String> login(LoginAnfrage loginAnfrage) {
        if (loginAnfrage.getRole().equals("Studentin")) {
            return loginStudentin(loginAnfrage.getUsername(), loginAnfrage.getPassword());
        } else {
            return loginPB(loginAnfrage.getUsername(), loginAnfrage.getPassword());
        }
    }

    /**
     * Führt den Login-Prozess für Studenten durch.
     * <p>
     * Die Methode sucht die Matrikelnummer in der Datenbank und validiert das Passwort.
     * </p>
     *
     * @param username Der Benutzername (Matrikelnummer) des Studenten.
     * @param password Das Passwort des Studenten.
     * @return Eine {@link Optional} Matrikelnummer bei erfolgreicher Authentifizierung.
     */
    private Optional<String> loginStudentin(String username, String password) {
        return studentinRepository.findByMatrikelnummer(username)
                                  .filter(studentin -> passwordEncoder.matches(password, studentin.getPassword()))
                                  .map(Studentin::getMatrikelnummer);
    }

    /**
     * Führt den Login-Prozess für Praktikumsbeauftragte durch.
     * <p>
     * Diese Methode sucht nach dem Benutzernamen und validiert das zugehörige Passwort.
     * </p>
     *
     * @param username Der Benutzername des Praktikumsbeauftragten.
     * @param password Das Passwort des Praktikumsbeauftragten.
     * @return Eine {@link Optional} mit dem generischen Identifikator "PRAKTIKUMSBEAUFTRAGTER" bei erfolgreicher Authentifizierung.
     */
    private Optional<String> loginPB(String username, String password) {
        return praktikumsbeauftragterRepository.findByUsername(username)
                                               .filter(pb -> passwordEncoder.matches(password, pb.getPasswort()))
                                               .map(pb -> "PRAKTIKUMSBEAUFTRAGTER"); //
    }

}