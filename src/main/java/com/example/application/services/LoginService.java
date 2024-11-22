package com.example.application.services;

import com.example.application.models.LoginAnfrageStudentin;
import com.example.application.models.Studentin;
import com.example.application.repositories.StudentinRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final StudentinRepository studentinRepository;

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
}
