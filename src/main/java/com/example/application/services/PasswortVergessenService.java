package com.example.application.services;

import com.example.application.models.Sicherheitsantwort;
import com.example.application.models.Sicherheitsfrage;
import com.example.application.repositories.SicherheitsantwortRepository;
import com.example.application.repositories.SicherheitsfrageRepository;
import com.example.application.repositories.StudentinRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PasswortVergessenService {

    private final SicherheitsantwortRepository sicherheitsantwortRepository;
    private final StudentinRepository studentinRepository;
    private final SicherheitsfrageRepository sicherheitsfrageRepository;

    public boolean eingabeMatrikelnummer(String matrikelnummer) {
        return studentinRepository.findByMatrikelnummer(matrikelnummer).isPresent();
    }

    public Sicherheitsfrage getSicherheitsfrage(String matrikelnummer) {
        if(sicherheitsantwortRepository.findByMatrikelnummer(matrikelnummer).isPresent()) {
            long frageId = sicherheitsantwortRepository.findByMatrikelnummer(matrikelnummer).get().getFrage();
            if(sicherheitsfrageRepository.findById(frageId).isPresent()) {
                return sicherheitsfrageRepository.findById(frageId).get();
            }
            else{
                throw new IllegalStateException("Fehler beim Finden der Sicherheitsfrage.");
            }
        }
        else{
            throw new IllegalStateException("Fehler beim Finden der Sicherheitsantwort.");
        }
    }

    public boolean eingabeAntwort(String matrikelnummer, String antwort) {
        Sicherheitsantwort requiredAnswer = sicherheitsantwortRepository.findByMatrikelnummer(matrikelnummer).get();
        Sicherheitsantwort enteredAnswer = new Sicherheitsantwort(requiredAnswer.getFrage(), matrikelnummer, antwort);
        return requiredAnswer.equals(enteredAnswer);
    }

    public String ausgabeVergessenesPasswort(String matrikelnummer){
        if(studentinRepository.findByMatrikelnummer(matrikelnummer).isPresent()) {
            return studentinRepository.findByMatrikelnummer(matrikelnummer).get().getPassword();
        }
        else{
            throw new IllegalStateException("Fehler beim Finden der Studentin.");
        }
    }
}
