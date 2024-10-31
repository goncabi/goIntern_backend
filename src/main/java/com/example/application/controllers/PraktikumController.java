package com.example.application.controllers;


import org.springframework.web.bind.annotation.*;
import com.example.application.services.PraktikumService;

@RestController
@RequestMapping("/api/praktika")
public class PraktikumController {
    private final PraktikumService service;

    public PraktikumController(PraktikumService service) {
        this.service = service;
    }


    // Weitere Endpunkte für Aktionen wie "beantragen", "bestaetigen" etc.
}