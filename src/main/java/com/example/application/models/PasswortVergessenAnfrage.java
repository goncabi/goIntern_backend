package com.example.application.models;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class PasswortVergessenAnfrage {
    private String matrikelnummer;
    private Set<Sicherheitsantwort> enteredAnswers;

    public PasswortVergessenAnfrage(String matrikelnummer, String answer1, String answer2, String answer3) {
        this.matrikelnummer = matrikelnummer;
        enteredAnswers = new HashSet<>();
        enteredAnswers.add(new Sicherheitsantwort(1, matrikelnummer, answer1));
        enteredAnswers.add(new Sicherheitsantwort(2, matrikelnummer, answer2));
        enteredAnswers.add(new Sicherheitsantwort(3, matrikelnummer, answer3));
    }
}
