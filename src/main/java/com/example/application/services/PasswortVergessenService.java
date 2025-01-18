package com.example.application.services;

import com.example.application.models.Sicherheitsantwort;
import com.example.application.models.Studentin;
import com.example.application.repositories.SicherheitsantwortRepository;
import com.example.application.repositories.SicherheitsfrageRepository;
import com.example.application.repositories.StudentinRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service-Klasse für das Zurücksetzten von Passwörtern.
 *
 * <p></p>Diese Klasse hilft dabei, Nutzern ihre Passwörter zurückzusetzten. Hier prüfen wir, ob ein Nutzer existiert, geben die passende Sicherheitsfrage aus und ändern das Passwort, wenn die Antwort korrekt ist.</p>
 *
 * <h2>Hauptfunktionen:</h2>
 * <ul>
 *     <li></li>Prüfen, ob eine Matrikelnummer existiert.</li>
 *     <li></li>Sicherheitsfragen fpr eine Matrikelnummer abrufen.</li>
 *     <li> Passwort zurücksetzten, wenn die Antwort stimmt.</li>
 * </ul>
 */

@Service
@AllArgsConstructor
public class PasswortVergessenService {

    private final SicherheitsantwortRepository sicherheitsantwortRepository;
    private final StudentinRepository studentinRepository;
    private final SicherheitsfrageRepository sicherheitsfrageRepository;

    public boolean eingabeMatrikelnummer(String matrikelnummer) {
        return studentinRepository.findByMatrikelnummer(matrikelnummer).isPresent();
    }

    public String getSicherheitsfrage(String matrikelnummer) {
        if(sicherheitsantwortRepository.findByMatrikelnummer(matrikelnummer).isPresent()) {
            long frageId = sicherheitsantwortRepository.findByMatrikelnummer(matrikelnummer).get().getFrage();
            if(sicherheitsfrageRepository.findById(frageId).isPresent()) {
                return sicherheitsfrageRepository.findById(frageId).get().getFrage();
            }
            else{
                throw new IllegalStateException("Fehler beim Finden der Sicherheitsfrage.");
            }
        }
        else{
            throw new IllegalStateException("Fehler beim Finden der Sicherheitsantwort.");
        }
    }

    public boolean resetPassword(String matrikelnummer, String enteredAnswer, String passwort) {
        if(sicherheitsantwortRepository.findByMatrikelnummer(matrikelnummer).isPresent()) {
            String requiredAnswer = sicherheitsantwortRepository.findByMatrikelnummer(matrikelnummer).get().getAntwort();
            if(requiredAnswer.equals(enteredAnswer)){
                if(studentinRepository.findByMatrikelnummer(matrikelnummer).isPresent()) {
                    Studentin studentin = studentinRepository.findByMatrikelnummer(matrikelnummer).get();
                    studentin.setPassword(passwort);
                    studentinRepository.save(studentin);
                    return true;
                }
                else{
                    throw new IllegalStateException("Fehler beim Finden der Studentin.");
                }
            }
            else{
                return false;
            }
        }
        else{
            throw new IllegalStateException("Fehler beim Finden der Sicherheitsantwort.");
        }
    }

}
