package com.example.application.services;

import com.example.application.models.LoginAnfragePB;
import com.example.application.models.LoginAnfrageStudentin;
import com.example.application.models.Praktikumsbeauftragter;
import com.example.application.models.Studentin;
import com.example.application.repositories.PraktikumsantragRepository;
import com.example.application.repositories.PraktikumsbeauftragterRepository;
import com.example.application.repositories.StudentinRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final StudentinRepository studentinRepository;
    private final PraktikumsbeauftragterRepository praktikumsbeauftragterRepository;


    public boolean login(LoginAnfrageStudentin loginAnfrage) {
        boolean loginSuccessful;
        if(studentinRepository.findByMatrikelnummer(loginAnfrage.getMatrikelnummer()).isPresent()) {
            Studentin studentin = studentinRepository.findByMatrikelnummer(loginAnfrage.getMatrikelnummer()).get();
            loginSuccessful = studentin.getPassword().equals(loginAnfrage.getPasswort());
        }
        else{
            throw new IllegalStateException("Matrikelnummer falsch oder nicht registriert");
        }
        return loginSuccessful;
    }

    public boolean login(LoginAnfragePB loginAnfrage) {
        boolean loginSuccessful;
        if(praktikumsbeauftragterRepository.findByUsername(loginAnfrage.getUsername()).isPresent()) {
            Praktikumsbeauftragter praktikumsbeauftragter = praktikumsbeauftragterRepository.findByUsername(loginAnfrage.getUsername()).get();
            loginSuccessful = praktikumsbeauftragter.getPasswort().equals(loginAnfrage.getPasswort());
        }
        else{
            throw new IllegalStateException("Username ist falsch");
        }
        return loginSuccessful;
    }


}
