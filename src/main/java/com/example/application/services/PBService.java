package com.example.application.services;

import com.example.application.models.*;
import com.example.application.models.benachrichtigung.Benachrichtigung;
import com.example.application.models.benachrichtigung.LeseStatus;
import com.example.application.repositories.PraktikumsantragRepository;
import com.example.application.repositories.StudentinRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import com.example.application.repositories.PBRepository;
import java.util.Date;

@Service
@AllArgsConstructor
public class PBService implements CommandLineRunner {

    private StudentinRepository studentinRepository;
    private final PBRepository praktikumsbeauftragterRepository;
    private final PraktikumsantragRepository praktikumsantragRepository;

    @Override
    public void run(String... args) throws Exception {
        praktikumsbeauftragterRepository.save(new Praktikumsbeauftragter("Jörn Freiheit", "AbInDieFreiheit13579!", AppUserRole.ADMIN));
    }

    //Methode antragGenehmigen setzt Status auf zugelassen und fügt Nachricht Element der Nachrichtenliste in Studentin hinzu
    public void antragGenehmigen(Long antragsID, String matrikelnummer) {
        Praktikumsantrag antrag = praktikumsantragRepository.findById(antragsID)
                .orElseThrow(() -> new IllegalArgumentException("Antrag nicht gefunden: " + antragsID));

        Studentin studentin = studentinRepository.findByMatrikelnummer(matrikelnummer)
                .orElseThrow(() -> new IllegalArgumentException("Studentin nicht gefunden: " + matrikelnummer));

        antrag.setStatusAntrag(Status_Antrag.ZUGELASSEN);
        studentin.addNachricht(new Benachrichtigung("Dein Antrag wurde genehmigt.", new Date(), LeseStatus.UNGELESEN));
    }

    public void antragAblehnen(Praktikumsantrag antrag) {
        String matrikelnummer = antrag.getMatrikelnummer();
        String studentinName = antrag.getNameStudentin();
        String input = "Textfeld im Frontend für Ablehnungsbegründung";
        String begruendung = "Sehr geehrte Frau " + studentinName + ", Ihr Praktikumsantrag wurde mit folgender Begründung abgelehnt: " + input;
        Date aktuellesDatum = new Date();
        Benachrichtigung ablehnungsNotiz = new Benachrichtigung(begruendung, aktuellesDatum, LeseStatus.UNGELESEN);

        if (studentinRepository.findByMatrikelnummer(matrikelnummer).isPresent()) {
            antrag.setStatusAntrag(Status_Antrag.ABGELEHNT);
            Studentin studentin = studentinRepository.findByMatrikelnummer(matrikelnummer).get();
            studentin.addNachricht(ablehnungsNotiz);
        }
    }

    public void antragUebermitteln(Praktikumsantrag antrag, String username) {
        Praktikumsbeauftragter pb = praktikumsbeauftragterRepository.findByUsername(username)
                                                                    .orElseThrow(() -> new IllegalArgumentException("Kein Praktikumsbeauftragter mit dem Benutzernamen " + username + " gefunden."));

        Benachrichtigung neueBenachrichtigung = new Benachrichtigung(
                "Ein neuer Antrag mit der Matrikelnummer " + antrag.getMatrikelnummer() + " wurde übermittelt.",
                new Date(),
                LeseStatus.UNGELESEN
        );

        pb.getBenachrichtigungList().add(neueBenachrichtigung);

    }
}