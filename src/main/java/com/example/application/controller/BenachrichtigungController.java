package com.example.application.controller;

import com.example.application.models.benachrichtigung.Benachrichtigung;
import com.example.application.services.BenachrichtigungService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BenachrichtigungController {
    private final BenachrichtigungService benachrichtigungService;


    @GetMapping("/{nutzername}/nachrichten")
    public List<Benachrichtigung> getBenachrichtigung(@PathVariable String nutzername) {
        return benachrichtigungService.alleLesen(nutzername);
    }

    @GetMapping("/{nutzername}/nachrichten/ungelesen")
    public List<Benachrichtigung> getUngeleseneBenachrichtigung(@PathVariable String nutzername) {
        return benachrichtigungService.ungeleseneLesen(nutzername);
    }

    @GetMapping("/{nutzername}")
    public boolean getZeichenBeiUngelesenen(@PathVariable String nutzername) {
        return benachrichtigungService.existierenUngelesene(nutzername);
    }

    //Für Studentin
    @GetMapping("/{matrikelnummer}")
    public ResponseEntity<String> getNotiz(@PathVariable String matrikelnummer) {
        if(benachrichtigungService.notizAusgeben(matrikelnummer).isPresent()){
            return ResponseEntity.ok(benachrichtigungService.notizAusgeben(matrikelnummer).get().getNachricht());
        }
        else{
            return ResponseEntity.badRequest().body("Keine Nachricht vorhanden.");
        }
    }
}
