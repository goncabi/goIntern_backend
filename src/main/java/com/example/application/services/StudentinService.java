package com.example.application.services;

import com.example.application.models.Studentin;
import com.example.application.repositories.StudentinRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentinService {

    private final StudentinRepository studentinRepository;

    public void signUpUser(Studentin studentin) {
        boolean userExists = studentinRepository.findByMatrikelnummer(studentin.getMatrikelnummer()).isPresent();
        if(userExists) {
            throw new IllegalStateException("Matrikelnummer existiert bereits.");
        }
        studentinRepository.save(studentin);
    }
}
