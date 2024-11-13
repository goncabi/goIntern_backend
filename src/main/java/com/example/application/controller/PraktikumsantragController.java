package com.example.application.controller;

import com.example.application.models.Praktikumsantrag;
import com.example.application.models.Status_Antrag;
import com.example.application.services.PraktikumsantragService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // wandelt Rückgabewerte von Methoden automatisch in JSON um, damit sie über HTTP verwendet werden können.
@RequestMapping("/api/antrag")//legt die Basis-URL für alle Endpunkte fest.
@AllArgsConstructor //generiert automatisch einen Konstruktor

public class PraktikumsantragController {
    PraktikumsantragService praktikumsantragService;

    @PostMapping("/praktikumsantrag")//Generiert Post-Endpunkt
    public ResponseEntity<String> PraktikumsantragErstellen(@Valid @RequestBody Praktikumsantrag antrag) { // Gibt eine Bestätigung mit HTTP-Status 200 OK zurück,
        // dass der Antrag erfolgreich gespeichert wurde.

        antrag.setStatusAntrag(Status_Antrag.INBEARBEITUNG);
        praktikumsantragService.antragSpeichern(antrag);
        return ResponseEntity.ok("Praktikumsantrag wurde elfolgreich gespeichert!");
    }
}
