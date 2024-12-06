package com.example.application.services.registrierung;

import com.example.application.models.*;
import com.example.application.services.StudentinService;
import com.example.application.models.RegistrierungsAnfrage;
import com.example.application.repositories.SicherheitsantwortRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@AllArgsConstructor
public class RegistrierungService {

    private final StudentinService studentinService;
    private final SicherheitsantwortRepository sicherheitsantwortRepository;

    public void registrieren(RegistrierungsAnfrage anfrage) {
        boolean passwoerterIdentisch = Objects.equals(anfrage.getPasswort1(), anfrage.getPasswort2());
        if (!passwoerterIdentisch) {
            throw new IllegalStateException("Eingegebene Passwörter sind nicht identisch.");
        }
        else {
            studentinService.signUpUser(
                    new Studentin(anfrage.getMatrikelnummer(), anfrage.getPasswort1(), AppUserRole.USER));
            Sicherheitsantwort antwort = new Sicherheitsantwort(anfrage.getFrage(), anfrage.getMatrikelnummer(), anfrage.getAntwort());
            sicherheitsantwortRepository.save(antwort);
        }
    }
}
