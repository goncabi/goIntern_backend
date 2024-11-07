package com.example.application.controllers;


import org.springframework.web.bind.annotation.*;
import com.example.application.services.PraktikumsstelleService;

@RestController
@RequestMapping("/api/praktika")
public class PraktikumController {
    private final PraktikumsstelleService service;

    public PraktikumController(PraktikumsstelleService service) {
        this.service = service;
    }


    // Weitere Endpunkte für Aktionen wie "beantragen", "bestaetigen" etc.
}