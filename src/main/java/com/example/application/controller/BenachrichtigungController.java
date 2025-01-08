package com.example.application.controller;

import com.example.application.models.benachrichtigung.Benachrichtigung;
import com.example.application.services.BenachrichtigungService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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


    @GetMapping("/nachrichten/{username}")
    public List<Benachrichtigung> getBenachrichtigung(@PathVariable String username) {
        return benachrichtigungService.alleLesen(username);
    }

    @GetMapping("/glocke/{nutzername}")
    public ResponseEntity<String> getZeichenBeiUngelesenen(@PathVariable String nutzername) {
        try{
            if(benachrichtigungService.existierenUngelesene(nutzername)){
                return ResponseEntity.ok("Ungelesene vorhanden.");
            }
            else{
                return ResponseEntity.ok("Ungelesene nicht vorhanden.");
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein unerwarteter Fehler ist aufgetreten.");
        }
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
