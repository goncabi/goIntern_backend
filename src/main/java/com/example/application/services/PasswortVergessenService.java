package com.example.application.services;

import com.example.application.models.PasswortVergessenAnfrage;
import com.example.application.models.Sicherheitsantwort;
import com.example.application.repositories.SicherheitsantwortRepository;
import com.example.application.repositories.StudentinRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PasswortVergessenService {

    private final SicherheitsantwortRepository sicherheitsantwortRepository;
    private final StudentinRepository studentinRepository;

    public boolean passwortVergessen(PasswortVergessenAnfrage anfrage) {
        boolean answerCorrect;
        if(studentinRepository.findByMatrikelnummer(anfrage.getMatrikelnummer()).isPresent()) {
            Sicherheitsantwort requiredAnswer = sicherheitsantwortRepository.findByMatrikelnummer(anfrage.getMatrikelnummer()).get();
            answerCorrect = requiredAnswer.equals(anfrage.getSicherheitsantwort());
        }
        else{
            throw new IllegalStateException("User mit genannter Matrikelnummer existiert nicht.");
        }
        return answerCorrect;
    }

}
