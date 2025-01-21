package com.example.application.services;

import com.example.application.models.Benachrichtigung;
import com.example.application.repositories.BenachrichtigungRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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

    /**
     * Liest alle benachrichtigungen für einen bestimmten Empfänger.
     * @param empfaenger_in Die Matrikelnummer des Empfängers.
     * @return eine Liste von Benachrichtigungen, sortiert nach datum
     */

    public List<Benachrichtigung> alleLesen(String empfaenger_in){
        return benachrichtigungRepository.findByEmpfaengerOrderByDatumDesc(empfaenger_in);
    }

    /**
     * löscht alle Benachrichtigungen für einen bestimmten Empfänger.
     * @param empfaenger_in die Matrikelnummer des Empfängers
     */

    public void nachrichtenLoeschen(String empfaenger_in){
        List<Benachrichtigung> nachrichtenListe = benachrichtigungRepository.findByEmpfaengerOrderByDatumDesc(empfaenger_in);
        benachrichtigungRepository.deleteAll(nachrichtenListe);
    }
}
