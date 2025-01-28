package com.example.application.services;

import com.example.application.models.AppUserRole;
import com.example.application.models.Benachrichtigung;
import com.example.application.models.BenachrichtigungWichtigkeit;
import com.example.application.models.Praktikumsbeauftragter;
import com.example.application.repositories.BenachrichtigungRepository;
import com.example.application.repositories.PBRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Service Klasse für die Verwaltung von Benachrichtigungen.
 *
 * Die Klasse bietet Funktionen, um Benachrichtigungen für Nutzer zu lesen und zu löschen.
 */

@Service
@AllArgsConstructor
public class BenachrichtigungService {

    private final BenachrichtigungRepository benachrichtigungRepository;
    private final PBRepository pbRepository;

    /**
     * Liest alle benachrichtigungen für einen bestimmten Empfänger.
     * @param empfaenger_in Die Matrikelnummer des Empfängers.
     * @return eine Liste von Benachrichtigungen, sortiert nach datum
     */
    public List<Benachrichtigung> alleLesen(String empfaenger_in){
        return benachrichtigungRepository.findByEmpfaengerOrderByDatumDesc(empfaenger_in);
    }

    /**
     * löscht alle "unwichtigen" Benachrichtigungen für einen bestimmten Empfänger (d.h. alle Nachrichten außer die Arbeitstagenachricht)
     * @param empfaenger_in die Matrikelnummer des Empfängers
     */
    public void unwichtigeNachrichtenLoeschen(String empfaenger_in){
        List<Benachrichtigung> nachrichtenListe = benachrichtigungRepository.findByWichtigkeitAndEmpfaenger(BenachrichtigungWichtigkeit.UNWICHTIG, empfaenger_in);
        benachrichtigungRepository.deleteAll(nachrichtenListe);
    }

    /**
     * Löscht die Nachricht mit den Arbeitstagen für eine bestimmte Studentin bei der Studentin und dem PB.
     * @param empfaenger_in Matrikelnummer der Studentin
     */
    public void arbeitstagenachrichtenLoeschen(String empfaenger_in){
        List<Benachrichtigung> nachrichtenListe = benachrichtigungRepository.findByWichtigkeit(BenachrichtigungWichtigkeit.WICHTIG);
        for (Benachrichtigung b : nachrichtenListe) {
            if(b.getEmpfaenger().equals(empfaenger_in)){
                benachrichtigungRepository.delete(b);
            }
            if(b.getNachricht().startsWith(empfaenger_in)){
                benachrichtigungRepository.delete(b);
            }
        }
    }

    /**
     * Speichert die Nachricht mit den bereits absolvierten Arbeitstagen für die Studentin und für den PB.
     * @param benachrichtigung Nachricht mit der Matrikelnummer der Studentin als Empfänger
     *                         und der bereits absolvierten Anzahl der Arbeitstage.
     */
    public void arbeitstageNachrichtenSpeichern(Benachrichtigung benachrichtigung){
        //Nachricht an Studentin
        benachrichtigung.setWichtigkeit(BenachrichtigungWichtigkeit.WICHTIG);
        benachrichtigungRepository.save(benachrichtigung);

        //Nachricht an PB
        Praktikumsbeauftragter pb = pbRepository.findByUserRole(AppUserRole.PRAKTIKUMSBEAUFTRAGTER)
                .orElseThrow(() -> new IllegalArgumentException("Kein Praktikumsbeauftragter mit der Rolle ADMIN gefunden."));

        Benachrichtigung neueBenachrichtigung = new Benachrichtigung(
                benachrichtigung.getEmpfaenger() + ": " + benachrichtigung.getNachricht(),
                new Date(),
                pb.getUsername()
        );
        neueBenachrichtigung.setWichtigkeit(BenachrichtigungWichtigkeit.WICHTIG);
        benachrichtigungRepository.save(neueBenachrichtigung);
    }
}
