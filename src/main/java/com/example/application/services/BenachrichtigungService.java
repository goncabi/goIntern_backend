package com.example.application.services;

import com.example.application.models.benachrichtigung.Benachrichtigung;
import com.example.application.repositories.BenachrichtigungRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class BenachrichtigungService {

    private final BenachrichtigungRepository benachrichtigungRepository;

    public List<Benachrichtigung> alleLesen(String empfaenger_in){
        return benachrichtigungRepository.findByEmpfaengerOrderByDatum(empfaenger_in);
    }

    public void nachrichtenLoeschen(String empfaenger_in){
        List<Benachrichtigung> nachrichtenListe = benachrichtigungRepository.findByEmpfaengerOrderByDatum(empfaenger_in);
        benachrichtigungRepository.deleteAll(nachrichtenListe);
    }
}
