package com.example.application.services;

import com.example.application.models.Praktikumsbeauftragter;
import com.example.application.repositories.PraktikumsantragRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.application.repositories.PraktikumsbeauftragterRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PBService {

    private final PraktikumsbeauftragterRepository praktikumsbeauftragterRepository;

    public void signUpUser(Praktikumsbeauftragter praktikumsbeauftragter) {
        boolean userExists = praktikumsbeauftragterRepository.findByUsername(praktikumsbeauftragter.getUsername()).isPresent();
        if(userExists) {
            throw new IllegalStateException("Username existiert bereits");
        }
        praktikumsbeauftragterRepository.save(praktikumsbeauftragter);
    }
}
