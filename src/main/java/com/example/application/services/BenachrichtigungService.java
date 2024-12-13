package com.example.application.services;

import com.example.application.models.benachrichtigung.Benachrichtigung;
import com.example.application.models.benachrichtigung.LeseStatus;
import com.example.application.repositories.BenachrichtigungRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class BenachrichtigungService {

    private final BenachrichtigungRepository benachrichtigungRepository;

    public List<Benachrichtigung> alleLesen(String empfaenger_in){
        return benachrichtigungRepository.findByEmpfaengerOrderByDatum(empfaenger_in);
    }

    public Optional<Benachrichtigung> notizAusgeben(String matrikelnummer){
        return benachrichtigungRepository.findByEmpfaenger(matrikelnummer);
    }

    public List<Benachrichtigung> ungeleseneLesen(String empfaenger_in){
        List<Benachrichtigung> nachrichtenListe = benachrichtigungRepository.findByEmpfaengerOrderByDatum(empfaenger_in);
        List<Benachrichtigung> ungelesene = new ArrayList<>();
        for(Benachrichtigung b : nachrichtenListe){
            if(b.getLeseStatus() == LeseStatus.UNGELESEN){
                ungelesene.add(b);
            }
        }
        return ungelesene;
    }

    public boolean existierenUngelesene(String empfaenger_in){
        List<Benachrichtigung> nachrichtenListe = benachrichtigungRepository.findByEmpfaengerOrderByDatum(empfaenger_in);
        for(Benachrichtigung b : nachrichtenListe){
            if(b.getLeseStatus() == LeseStatus.UNGELESEN){
                return true;
            }
        }
        return false;
    }

}
