package com.example.application.controller;

import com.example.application.models.Praktikumsantrag;
import com.example.application.services.PraktikumsantragService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping//hier kommt die Verlinkung zum Frontend bzw. die http

public class PraktikumsantragController {
    @Autowired
    PraktikumsantragService praktikumsantragService;

    @PostMapping
    public ResponseEntity<String> PraktikumsantragErstellen(@Valid @RequestBody Praktikumsantrag antrag) {
        praktikumsantragService.antragSpeichern(antrag);
        return ResponseEntity.ok("Praktikumsantrag wurde elfolgreich gespeichert!");
    }
}
