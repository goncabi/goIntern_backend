package com.example.application.services;

import com.example.application.models.PasswortVergessenAnfrage;
import com.example.application.models.Sicherheitsantwort;
import com.example.application.models.Studentin;
import com.example.application.repositories.SicherheitsantwortRepository;
import com.example.application.repositories.StudentinRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class PasswortVergessenService {

    private final SicherheitsantwortRepository sicherheitsantwortRepository;
    private final StudentinRepository studentinRepository;

    public boolean passwortVergessen(PasswortVergessenAnfrage anfrage) {
        boolean answersCorrect;
        if(studentinRepository.findByMatrikelnummer(anfrage.getMatrikelnummer()).isPresent()) {
            Set<Sicherheitsantwort> requiredAnswers = sicherheitsantwortRepository.findByMatrikelnummer(anfrage.getMatrikelnummer());
            answersCorrect = requiredAnswers.containsAll(anfrage.getEnteredAnswers());
        }
        else{
            throw new IllegalStateException("User mit genannter Matrikelnummer existiert nicht.");
        }
        return answersCorrect;
    }

}
