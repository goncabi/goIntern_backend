package com.example.application.services;

import com.example.application.models.*;
import com.example.application.repositories.PBRepository;
import com.example.application.repositories.StudentinRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class LoginService {

    private final StudentinRepository studentinRepository;
    private final PBRepository praktikumsbeauftragterRepository;

    public boolean login(LoginAnfrage loginAnfrage) {
        if(loginAnfrage.getRole().equals("Student/in")) {
            return loginStudentin(loginAnfrage.getUsername(), loginAnfrage.getPassword());
        }
        else {
            return loginPB(loginAnfrage.getUsername(), loginAnfrage.getPassword());
        }
    }
    private boolean loginStudentin(String username, String password) {
        boolean loginSuccessful;
        if(studentinRepository.findByMatrikelnummer(username).isPresent()) {
            Studentin studentin = studentinRepository.findByMatrikelnummer(username).get();
            loginSuccessful = studentin.getPassword().equals(password);
        }
        else{
            loginSuccessful = false;
        }
        return loginSuccessful;
    }

    private boolean loginPB(String username, String password) {
        boolean loginSuccessful;
        if(praktikumsbeauftragterRepository.findByUsername(username).isPresent()) {
            Praktikumsbeauftragter praktikumsbeauftragter = praktikumsbeauftragterRepository.findByUsername(username).get();
            loginSuccessful = praktikumsbeauftragter.getPasswort().equals(password);
        }
        else{
            loginSuccessful = false;
        }
        return loginSuccessful;
    }

}
