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

@RestController
@RequestMapping("/api/antrag")//hier kommt die Verlinkung zum Frontend bzw. die http
@AllArgsConstructor

public class PraktikumsantragController {
    PraktikumsantragService praktikumsantragService;

    @PostMapping("/praktikumsantrag")//hier kommt die Verlinkung
    public ResponseEntity<String> PraktikumsantragErstellen(@Valid @RequestBody Praktikumsantrag antrag) {
        antrag.setStatusAntrag(Status_Antrag.INBEARBEITUNG);
        praktikumsantragService.antragSpeichern(antrag);
        return ResponseEntity.ok("Praktikumsantrag wurde elfolgreich gespeichert!");
    }
}
