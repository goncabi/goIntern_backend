package com.example.application.services;

import com.example.application.models.Praktikumsbeauftragter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.application.repositories.PBRepository;

@Service
@AllArgsConstructor
public class PBService {

    private final PBRepository praktikumsbeauftragterRepository;

    public void signUpUser(Praktikumsbeauftragter praktikumsbeauftragter) {
        boolean userExists = praktikumsbeauftragterRepository.findByUsername(praktikumsbeauftragter.getUsername()).isPresent();
        if(userExists) {
            throw new IllegalStateException("Username existiert bereits");
        }
        praktikumsbeauftragterRepository.save(praktikumsbeauftragter);
    }
}
