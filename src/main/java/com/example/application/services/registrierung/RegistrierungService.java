package com.example.application.services.registrierung;

import com.example.application.models.*;
import com.example.application.repositories.StudentinRepository;
import com.example.application.models.RegistrierungsAnfrage;
import com.example.application.repositories.SicherheitsantwortRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@AllArgsConstructor
public class RegistrierungService {

    private final StudentinRepository studentinRepository;
    private final SicherheitsantwortRepository sicherheitsantwortRepository;

    public boolean registrieren(RegistrierungsAnfrage anfrage) {
        boolean passwoerterIdentisch = Objects.equals(anfrage.getPassword(), anfrage.getPasswordConfirm());
        if (!passwoerterIdentisch) {
            return false;
        }
        else {
            if(studentinRepository.findByMatrikelnummer(anfrage.getUsername()).isPresent()){
                return false;
            }
            else {
                Studentin studentin = new Studentin(anfrage.getUsername(), anfrage.getPassword(), AppUserRole.STUDENTIN);
                studentinRepository.save(studentin);

                int frageId = Integer.parseInt(anfrage.getFrageId());
                Sicherheitsantwort antwort = new Sicherheitsantwort(frageId, anfrage.getUsername(), anfrage.getAnswer());
                sicherheitsantwortRepository.save(antwort);

                return true;
            }
        }
    }
}
