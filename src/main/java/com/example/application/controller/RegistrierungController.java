package com.example.application.controller;

import com.example.application.models.RegistrierungsAnfrage;
import com.example.application.services.RegistrierungService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Der RegistrierungsController stellt REST-Endpunkte zur Verfügung, die die Registrierung von Benutzern ermöglichen.
 * <p> Dieser Controller ist mit dem Frontend über die Route "/api/auth" verknüpft. </p>
 * @author Noa Sauter
 */
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class RegistrierungController {
    /**
     * Service für die Verarbeitung von Registrierungsanfragen.
     */
    private RegistrierungService registrierungService;

    /**
     * Verarbeitet eine Registrierungsanfrage, die vom Client gesendet wurde.
     * @param anfrage Die Registrierungsanfrage, die die Benutzerdaten enthält.
     * @return Eine ResponseEntity mit einer Nachricht und dem entsprechenden HTTP-Status:
     *         <ul>
     *             <li>200 (OK), wenn die Registrierung erfolgreich war.</li>
     *             <li>400 (BAD_REQUEST), wenn die Matrikelnummer bereits registriert ist oder die Passwörter nicht übereinstimmen.</li>
     *             <li>500 (INTERNAL_SERVER_ERROR), wenn ein unerwarteter Fehler auftritt.</li>
     *         </ul>
     * @throws IllegalArgumentException wenn die Eingabedaten ungültig sind.
     */
    @PostMapping("/registrieren")
    public ResponseEntity<String> registrieren(@RequestBody RegistrierungsAnfrage anfrage){
        try {
            if(registrierungService.registrieren(anfrage)){
                return new ResponseEntity<>("Registration OK", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("Registration Failed", HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein unerwarteter Fehler ist aufgetreten.");
        }
    }
}
