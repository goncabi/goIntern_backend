package com.example.application.controller;

import com.example.application.models.RegistrierungsAnfrage;
import com.example.application.services.registrierung.RegistrierungService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")//hier kommt die Verlinkung zum Frontend bzw. die http
@AllArgsConstructor
public class RegistrierungController {

    private RegistrierungService registrierungService;

    @PostMapping("/registrieren")
    public String registrieren(@RequestBody RegistrierungsAnfrage anfrage){
        registrierungService.registrieren(anfrage);
        return "Registrierung erfolgreich";
    }
}
