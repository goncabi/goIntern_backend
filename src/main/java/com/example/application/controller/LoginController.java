package com.example.application.controller;

import com.example.application.models.LoginAnfrage;
import com.example.application.services.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * LoginController verwaltet die Authentifizierung von Benutzern.
 * <p>
 * Dieser Controller bietet Endpunkte für die Anmeldung von Benutzern
 * und verarbeitet die Login-Anfrage mit entsprechenden Rückgabewerten.
 * </p>
 *
 * <ul>
 *   <li>Verwendet die {@link LoginService}, um die Anmeldedaten zu prüfen.</li>
 *   <li>Unterstützt die JSON-Verarbeitung mit {@code @RestController}.</li>
 *   <li>Basis-Pfad: {@code /api/auth}</li>
 * </ul>
 *
 * @author Beyza Nur Acikgöz
 * @version 1.0
 */

@AllArgsConstructor //erstellt automatisch ein Konstruktor
@RestController //@RestController ermöglicht das Versenden und Empfangen von JSON und XML-Daten.
@RequestMapping("/api/auth") //definiert den Basis-Pfad für alle Endpunkte in dem Controller
public class LoginController {

    /**
     * Service, der die Logik zur Authentifizierung der Benutzer enthält.
     */
    private final LoginService loginService;

    /**
     * Bearbeitet die Login-Anfragen der Benutzer.
     * <p>
     * Diese Methode nimmt die Login-Daten ({@link LoginAnfrage}) vom Frontend entgegen,
     * überprüft sie mithilfe des {@link LoginService} und gibt eine Antwort zurück.
     * </p>
     *
     * @param loginAnfrage Die Login-Daten des Benutzers (z. B. Benutzername, Passwort, Rolle).
     * @return Eine {@link ResponseEntity}, die eine Erfolgsmeldung und gegebenenfalls
     *         die Matrikelnummer zurückgibt. Bei Fehlern wird ein entsprechender HTTP-Status gesendet.
     */
    // Durch PostMapping Annotation ist die Methode ResponseEntity() ein Endpunkt.
    // Post heißt, es darf von Frontend mit Post Backend zugegriffen werden.
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login (@RequestBody LoginAnfrage loginAnfrage)
        {
            try {
                Optional<String> matrikelnummerOptional = loginService.login(loginAnfrage);

                if(matrikelnummerOptional.isPresent()) {
                    String matrikelnummer = matrikelnummerOptional.get();

                    Map<String, String> response = new HashMap<>();
                    response.put("message",
                                 "Login OK");

                    // Füge Matrikelnummer nur für Studenten hinzu
                    if("Student/in".equals(loginAnfrage.getRole())) {
                        response.put("matrikelnummer",
                                     matrikelnummer);
                    }

                    return ResponseEntity.ok(response);
                } else {
                    Map<String, String> response = new HashMap<>();
                    response.put("message",
                                 "Login Failed");

                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                         .body(response);
                }
            }
            catch(Exception e) {
                Map<String, String> response = new HashMap<>();
                response.put("message",
                             "Ein unerwarteter Fehler ist aufgetreten.");

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                     .body(response);
            }
        }
    }

