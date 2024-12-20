package com.example.application.services;

import com.example.application.models.*;
import com.example.application.repositories.PBRepository;
import com.example.application.repositories.StudentinRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class LoginService {

    private final StudentinRepository studentinRepository;
    private final PBRepository praktikumsbeauftragterRepository;


    public Optional<String> login(LoginAnfrage loginAnfrage) {
        if (loginAnfrage.getRole().equals("Student/in")) {
            return loginStudentin(loginAnfrage.getUsername(), loginAnfrage.getPassword());
        } else {
            return loginPB(loginAnfrage.getUsername(), loginAnfrage.getPassword());
        }
    }

    private Optional<String> loginStudentin(String username, String password) {
        return studentinRepository.findByMatrikelnummer(username)
                                  .filter(studentin -> studentin.getPassword().equals(password))
                                  .map(Studentin::getMatrikelnummer);
    }

    private Optional<String> loginPB(String username, String password) {
        return praktikumsbeauftragterRepository.findByUsername(username)
                                               .filter(pb -> pb.getPasswort().equals(password))
                                               .map(pb -> "PRAKTIKUMSBEAUFTRAGTER"); // Identificador genérico
    }

}