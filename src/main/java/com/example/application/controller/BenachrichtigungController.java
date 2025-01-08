package com.example.application.controller;

import com.example.application.models.benachrichtigung.Benachrichtigung;
import com.example.application.services.BenachrichtigungService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/nachrichtenLoeschen/{username}")
    public ResponseEntity<String> deleteNachrichten(@PathVariable String username) {
        try {
            benachrichtigungService.nachrichtenLoeschen(username);
            return ResponseEntity.ok("Praktikumsantrag wurde erfolgreich gelöscht.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Fehler beim Löschen des Antrags: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ein unerwarteter Fehler ist aufgetreten.");
        }
    }
}
