package com.example.application.services;

import com.example.application.models.*;
import com.example.application.repositories.StudentinRepository;
import com.example.application.models.RegistrierungsAnfrage;
import com.example.application.repositories.SicherheitsantwortRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Objects;

/**
 * Der RegistrierungService bietet die Geschäftslogik zur Registrierung von neuen Benutzern (Studentinnen).
 * <p> Dieser Service überprüft die Gültigkeit der Registrierungsdaten und speichert neue Benutzer sowie die Sicherheitsantwort für die Benutzerauthentifizierung in der Datenbank. </p>
 * @author Noa Sauter
 */
@Service
@AllArgsConstructor
public class RegistrierungService {
    /**
     * Repository für den Zugriff auf Studentin-Datensätze in der Datenbank.
     */
    private final StudentinRepository studentinRepository;
    /**
     * Repository für den Zugriff auf Sicherheitsantwort-Datensätze in der Datenbank.
     */
    private final SicherheitsantwortRepository sicherheitsantwortRepository;

    /**
     * Registriert einen neuen Benutzer (Studentin) mit den angegebenen Registrierungsdaten.
     * <p>
     * Diese Methode führt folgende Schritte durch:
     * <ul>
     *     <li>Prüft, ob die angegebenen Passwörter übereinstimmen.</li>
     *     <li>Überprüft, ob die Matrikelnummer (Benutzername) bereits existiert.</li>
     *     <li>Speichert die neue Studentin in der Datenbank, falls die Matrikelnummer noch nicht existiert.</li>
     *     <li>Speichert die Sicherheitsantwort für die Authentifizierung in der Datenbank.</li>
     * </ul>
     * </p>
     * @param anfrage Die Registrierungsanfrage mit den Benutzerdaten.
     * @return {@code true}, wenn die Registrierung erfolgreich war, andernfalls {@code false}.
     *         <ul>
     *             <li>{@code false}, wenn die Passwörter nicht übereinstimmen.</li>
     *             <li>{@code false}, wenn die Matrikelnummer bereits registriert ist.</li>
     *         </ul>
     * @throws NumberFormatException wenn die Frage-ID in der Anfrage kein gültiger Integer-Wert ist.
     */
    public boolean registrieren(RegistrierungsAnfrage anfrage) {
        boolean passwoerterIdentisch = Objects.equals(anfrage.getPassword(), anfrage.getPasswordConfirm());
        if (!passwoerterIdentisch) {
            return false;
        }
        else {
            if(studentinRepository.findByMatrikelnummer(anfrage.getUsername()).isPresent()){
                return false;
            }
            else {
                Studentin studentin = new Studentin(anfrage.getUsername(), anfrage.getPassword(), AppUserRole.STUDENTIN);
                studentinRepository.save(studentin);

                int frageId = Integer.parseInt(anfrage.getFrageId());
                Sicherheitsantwort antwort = new Sicherheitsantwort(frageId, anfrage.getUsername(), anfrage.getAnswer());
                sicherheitsantwortRepository.save(antwort);

                return true;
            }
        }
    }
}
