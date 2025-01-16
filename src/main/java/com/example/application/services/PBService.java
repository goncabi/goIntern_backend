package com.example.application.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.application.models.*;
import com.example.application.models.Benachrichtigung;
import com.example.application.repositories.BenachrichtigungRepository;
import com.example.application.repositories.PraktikumsantragRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import com.example.application.repositories.PBRepository;
import java.util.Date;

@Service
@AllArgsConstructor // <- die Annotation macht einen Konstuktor, sodass ich keinen machen muss.

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

            //ab hier Bug von Fehlermeldung gefixt:
           //hier Syntax etwas anders weil anderes Package von JSON Libary und mit jsonNode:
            ObjectMapper objectMapper = new ObjectMapper();
            String kommentar = "";
            try {
                JsonNode jsonNode = objectMapper.readTree(ablehnenNotiz); //jsonNode statt JsonObject
                kommentar = jsonNode.get("kommentar").asText(); // kommentar ist ein Feld in dem JSON String
            }
            catch (JsonProcessingException ignored) { }
            String begruendung = "Sehr geehrte Frau " + antrag.getNameStudentin() +
                    ", Ihr Praktikumsantrag wurde mit folgender Begründung abgelehnt: " + kommentar; //kommentar ist ein String JSON String vom Frontend
            Benachrichtigung ablehnungsNotiz = new Benachrichtigung(begruendung, new Date(), antrag.getMatrikelnummer());

            antrag.setStatusAntrag(StatusAntrag.ABGELEHNT);
            benachrichtigungRepository.save(ablehnungsNotiz);
            return "Der Antrag von " + antrag.getNameStudentin() + "wurde erfolgreich abgelehnt und die Nachricht übermittelt.";
        }
        else{
            throw new IllegalStateException("Fehler beim Finden des Praktikumsantrags.");
        }

    }

    // die Methode "antragUebermitteln" erstellt eine neue Benachrichtigung fuer den Praktikumsbeauftragten:
    public void antragUebermitteln(Praktikumsantrag antrag) {

        Praktikumsbeauftragter pb = praktikumsbeauftragterRepository.findByUserRole(AppUserRole.PRAKTIKUMSBEAUFTRAGTER)
                                                                    .orElseThrow(() -> new IllegalArgumentException("Kein Praktikumsbeauftragter mit der Rolle ADMIN gefunden."));

        Benachrichtigung neueBenachrichtigung = new Benachrichtigung(
                "Ein neuer Antrag mit der Matrikelnummer " + antrag.getMatrikelnummer() + " wurde übermittelt.",
                new Date(),
                pb.getUsername()
        );
        benachrichtigungRepository.save(neueBenachrichtigung);
    }

    // die Methode "antragZureckgezogen" erstellt eine neue Benachrichtigung fuer den Praktikumbsbeauftragten,
    // dass der Antrag zurückgezogen wurde.
    public void antragZurueckgezogen(String matrikelnummer) {
        Praktikumsbeauftragter pb = praktikumsbeauftragterRepository.findByUserRole(AppUserRole.PRAKTIKUMSBEAUFTRAGTER)
                .orElseThrow(() -> new IllegalArgumentException("Kein Praktikumsbeauftragter mit der Rolle ADMIN gefunden."));

        Benachrichtigung neueBenachrichtigung = new Benachrichtigung(
                "Der bereits zugelassene Antrag mit der Matrikelnummer " + matrikelnummer + " wurde zurückgezogen.",
                new Date(),
                pb.getUsername()
        );
        benachrichtigungRepository.save(neueBenachrichtigung);
    }
}