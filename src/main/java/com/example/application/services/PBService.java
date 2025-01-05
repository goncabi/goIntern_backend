package com.example.application.services;

import com.example.application.models.*;
import com.example.application.models.benachrichtigung.Benachrichtigung;
import com.example.application.models.benachrichtigung.LeseStatus;
import com.example.application.repositories.BenachrichtigungRepository;
import com.example.application.repositories.PraktikumsantragRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import com.example.application.repositories.PBRepository;

import java.util.Date;

@Service
@AllArgsConstructor

public class PBService implements CommandLineRunner {

    private final PBRepository praktikumsbeauftragterRepository;
    private final BenachrichtigungRepository benachrichtigungRepository;
    private final PraktikumsantragRepository praktikumsantragRepository;

    @Override
    public void run(String... args) throws Exception {
        praktikumsbeauftragterRepository.save(new Praktikumsbeauftragter("Jörn Freiheit", "AbInDieFreiheit13579!", AppUserRole.PRAKTIKUMSBEAUFTRAGTER));
    }

    //Methode antragGenehmigen setzt Status auf zugelassen
    public Praktikumsantrag antragGenehmigen(String matrikelnummer) {
        Praktikumsantrag dbAntrag = praktikumsantragRepository.findByMatrikelnummer(matrikelnummer)
                .orElseThrow(() -> new IllegalArgumentException("Antrag wurde nicht gefunden"));
        dbAntrag.setStatusAntrag(StatusAntrag.ZUGELASSEN);
        return praktikumsantragRepository.save(dbAntrag); // Speichert aktualisierten Antrag
    }


    public String antragAblehnen(String matrikelnummer, String ablehnenNotiz) {
        if(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer).isPresent()) {
            Praktikumsantrag antrag = praktikumsantragRepository.findByMatrikelnummer(matrikelnummer).get();
            String begruendung = "Sehr geehrte Frau " + antrag.getNameStudentin() +
                    ", Ihr Praktikumsantrag wurde mit folgender Begründung abgelehnt: " + ablehnenNotiz;
            Benachrichtigung ablehnungsNotiz = new Benachrichtigung(begruendung, new Date(), LeseStatus.UNGELESEN, antrag.getMatrikelnummer());

            antrag.setStatusAntrag(StatusAntrag.ABGELEHNT);
            benachrichtigungRepository.save(ablehnungsNotiz);
            return "Der Antrag von " + antrag.getNameStudentin() + "wurde erfolgreich abgelehnt und die Nachricht übermittelt.";
        }
        else{
            throw new IllegalStateException("Fehler beim Finden des Praktikumsantrags.");
        }

    }

    public void antragUebermitteln(Praktikumsantrag antrag) {

        Praktikumsbeauftragter pb = praktikumsbeauftragterRepository.findByUserRole(AppUserRole.PRAKTIKUMSBEAUFTRAGTER)
                                                                    .orElseThrow(() -> new IllegalArgumentException("Kein Praktikumsbeauftragter mit der Rolle ADMIN gefunden."));

        Benachrichtigung neueBenachrichtigung = new Benachrichtigung(
                "Ein neuer Antrag mit der Matrikelnummer " + antrag.getMatrikelnummer() + " wurde übermittelt.",
                new Date(),
                LeseStatus.UNGELESEN,
                pb.getUsername()
        );
        benachrichtigungRepository.save(neueBenachrichtigung);
    }
}