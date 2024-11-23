package com.example.application.services;

import com.example.application.models.Praktikumsantrag;
import com.example.application.models.Status_Antrag;
import com.example.application.models.Studentin;
import com.example.application.models.benachrichtigung.Benachrichtigung;
import com.example.application.models.benachrichtigung.LeseStatus;
import com.example.application.repositories.StudentinRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PBService {

    private StudentinRepository studentinRepository;

    public void antragAblehnen(Praktikumsantrag antrag) {
        String matrikelnummer = antrag.getMatrikelnummer();
        String studentinName = antrag.getNameStudentin();
        String input = "Textfeld im Frontend für Ablehnungsbegründung";
        String begruendung = "Sehr geehrte Frau "+ studentinName + ", Ihr Praktikumsantrag wurde mit folgender Begründung abgelehnt: " + input;
        Date aktuellesDatum = new Date();
        Benachrichtigung ablehnungsNotiz = new Benachrichtigung(begruendung, aktuellesDatum, LeseStatus.UNGELESEN);

        if(studentinRepository.findByMatrikelnummer(matrikelnummer).isPresent()){
            antrag.setStatusAntrag(Status_Antrag.ABGELEHNT);
            Studentin studentin = studentinRepository.findByMatrikelnummer(matrikelnummer).get();
            studentin.addNachricht(ablehnungsNotiz);
        }
    }

}
