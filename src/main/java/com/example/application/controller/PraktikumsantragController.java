package com.example.application.controller;

import com.example.application.models.Praktikumsantrag;
import com.example.application.models.Status_Antrag;
import com.example.application.services.PraktikumsantragService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // wandelt Rückgabewerte von Methoden automatisch in JSON um, damit sie über HTTP verwendet werden können.
@RequestMapping("/api/antrag")//legt die Basis-URL für alle Endpunkte fest.
@AllArgsConstructor //generiert automatisch einen Konstruktor (@Autowired wird nicht mehr gebraucht)

public class PraktikumsantragController {
    PraktikumsantragService praktikumsantragService;

    @PostMapping("/praktikumsantrag")//Generiert Post-Endpunkt
    public ResponseEntity<String> PraktikumsantragErstellen(@Valid Praktikumsantrag antrag) { // Gibt eine Bestätigung mit HTTP-Status 200 OK zurück,
        // dass der Antrag erfolgreich gespeichert wurde.

        antrag.setStatusAntrag(Status_Antrag.INBEARBEITUNG);
        praktikumsantragService.antragStellen(antrag);
        return ResponseEntity.ok("Praktikumsantrag wurde erfolgreich gespeichert!");
    }

    //Daten aus der Datenbank löschen mit DeleteMapping

   @DeleteMapping("/{id}")
    public ResponseEntity<String> antragLoeschen(@PathVariable Long id) {
       try {
           praktikumsantragService.antragLoeschen(id);
       } catch (RuntimeException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
       }
       return ResponseEntity.ok("Praktikumsantrag mit ID: " + id + " wurde erfolgreich gelöscht.");
   }
    @GetMapping("/alle")
    public List<Praktikumsantrag> getAlleAntraege() {
        return praktikumsantragService.getAllAntraege();
    }



}



