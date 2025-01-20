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

/**
 * PBService dient der Verwaltung von Praktikumsanträgen und Benachrichtigungen für
 * den Praktikumsbeauftragten.
 */

@Service
@AllArgsConstructor // <- die Annotation macht einen Konstuktor, sodass ich keinen machen muss.

public class PBService implements CommandLineRunner {

    private final PBRepository praktikumsbeauftragterRepository;
    private final BenachrichtigungRepository benachrichtigungRepository;
    private final PraktikumsantragRepository praktikumsantragRepository;


    /**
     * Führt eine Initialisierungsaktion aus, indem ein Standard-Praktikumsbeauftragter in der Datenbank gespeichert wird.
     *
     * @param args Kommandozeilenargumente
     * @throws Exception wenn ein Fehler während der Ausführung auftritt
     */
    @Override
    public void run(String... args) throws Exception {
        praktikumsbeauftragterRepository.save(new Praktikumsbeauftragter("Jörn Freiheit", "AbInDieFreiheit13579!", AppUserRole.PRAKTIKUMSBEAUFTRAGTER));
    }



    /**
     * Genehmigt einen Praktikumsantrag, indem der Status auf "ZUGELASSEN" gesetzt wird.
     *
     * @param matrikelnummer Matrikelnummer des Antragsstellers
     * @return der aktualisierte Praktikumsantrag nach der Statusänderung
     * @throws IllegalArgumentException falls der Antrag nicht gefunden wird
     */
    public Praktikumsantrag antragGenehmigen(String matrikelnummer) {
        Praktikumsantrag dbAntrag = praktikumsantragRepository.findByMatrikelnummer(matrikelnummer)
                .orElseThrow(() -> new IllegalArgumentException("Antrag wurde nicht gefunden"));
        dbAntrag.setStatusAntrag(StatusAntrag.ZUGELASSEN);
        return praktikumsantragRepository.save(dbAntrag); // Speichert aktualisierten Antrag
    }

    /**
     * Lehnt einen Praktikumsantrag ab und erstellt eine Notiz mit der Begründung.
     *
     * @param matrikelnummer Matrikelnummer des Antragsstellers
     * @param ablehnenNotiz  JSON-String mit der Begründung für die Ablehnung
     * @return ein Erfolgs-PopUp
     * @throws IllegalStateException falls der Antrag nicht gefunden wird
     */
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

    /**
     * Erstellt eine Benachrichtigung für den Praktikumsbeauftragten, dass ein neuer Antrag eingegangen ist.
     *
     * @param antrag der eingereichte Praktikumsantrag
     */
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

    /**
     * Erstellt eine Benachrichtigung für den Praktikumsbeauftragten, dass ein zugelassener Antrag zurückgezogen wurde.
     *
     * @param matrikelnummer Matrikelnummer des Antrags
     */
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


    /**
     * Erstellt eine Benachrichtigung für den Praktikumsbeauftragten, dass ein neues Poster hochgeladen wurde.
     *
     * @param matrikelnummer Matrikelnummer des Antragsstellers
     */
    public void posterNachrichtUebermitteln(String matrikelnummer) {
        Praktikumsbeauftragter pb = praktikumsbeauftragterRepository.findByUserRole(AppUserRole.PRAKTIKUMSBEAUFTRAGTER)
                .orElseThrow(() -> new IllegalArgumentException("Kein Praktikumsbeauftragter mit der Rolle ADMIN gefunden."));

        Benachrichtigung neueBenachrichtigung = new Benachrichtigung(
                "Ein neues Poster mit der Matrikelnummer " + matrikelnummer + " wurde übermittelt.",
                new Date(),
                pb.getUsername()
        );
        benachrichtigungRepository.save(neueBenachrichtigung);
    }
}