package com.example.application.services;

<<<<<<< src/main/java/com/example/application/services/PBService.java
import com.example.application.models.Praktikumsantrag;
import com.example.application.models.Status_Antrag;
import com.example.application.models.Studentin;
import com.example.application.models.benachrichtigung.Benachrichtigung;
import com.example.application.models.benachrichtigung.LeseStatus;
import com.example.application.repositories.StudentinRepository;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import com.example.application.models.Praktikumsbeauftragter;
import com.example.application.repositories.PBRepository;

import java.util.Date;

@Service
@AllArgsConstructor
public class PBService {

    private StudentinRepository studentinRepository;
    private final PBRepository praktikumsbeauftragterRepository;

    public void signUpUser(Praktikumsbeauftragter praktikumsbeauftragter) {
            boolean userExists = praktikumsbeauftragterRepository.findByUsername(praktikumsbeauftragter.getUsername()).isPresent();
            if(userExists) {
                throw new IllegalStateException("Username existiert bereits");
            }
            praktikumsbeauftragterRepository.save(praktikumsbeauftragter);
        }

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
