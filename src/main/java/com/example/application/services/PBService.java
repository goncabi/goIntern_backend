package com.example.application.services;

import com.example.application.models.*;
import com.example.application.models.benachrichtigung.Benachrichtigung;
import com.example.application.models.benachrichtigung.LeseStatus;
import com.example.application.repositories.BenachrichtigungRepository;
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

    @Override
    public void run(String... args) throws Exception {
        praktikumsbeauftragterRepository.save(new Praktikumsbeauftragter("Jörn Freiheit", "AbInDieFreiheit13579!", AppUserRole.ADMIN));
    }

    //Methode antragGenehmigen setzt Status auf zugelassen und fügt Nachricht Element der Nachrichtenliste in Studentin hinzu
    public void antragGenehmigen(Praktikumsantrag antrag) {
        String matrikelnummer = antrag.getMatrikelnummer();
        antrag.setStatusAntrag(StatusAntrag.ZUGELASSEN);
        Benachrichtigung nachrichtStudentin = new Benachrichtigung("Dein Antrag wurde genehmigt.", new Date(), LeseStatus.UNGELESEN, matrikelnummer);
        benachrichtigungRepository.save(nachrichtStudentin);
    }

    public void antragAblehnen(Praktikumsantrag antrag) {
        String matrikelnummer = antrag.getMatrikelnummer();
        String studentinName = antrag.getNameStudentin();
        String input = "Textfeld im Frontend für Ablehnungsbegründung";
        String begruendung = "Sehr geehrte Frau " + studentinName + ", Ihr Praktikumsantrag wurde mit folgender Begründung abgelehnt: " + input;
        Date aktuellesDatum = new Date();
        Benachrichtigung ablehnungsNotiz = new Benachrichtigung(begruendung, aktuellesDatum, LeseStatus.UNGELESEN, matrikelnummer);

        antrag.setStatusAntrag(StatusAntrag.ABGELEHNT);
        benachrichtigungRepository.save(ablehnungsNotiz);
    }

    public void antragUebermitteln(Praktikumsantrag antrag) {

        Praktikumsbeauftragter pb = praktikumsbeauftragterRepository.findByUserRole(AppUserRole.ADMIN)
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