package com.example.application.controller;

import com.example.application.models.AppUserRole;
import com.example.application.models.Benachrichtigung;
import com.example.application.models.Praktikumsbeauftragter;
import com.example.application.repositories.BenachrichtigungRepository;
import com.example.application.repositories.PBRepository;
import com.example.application.services.BenachrichtigungService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * BenachrichtigungController verwaltet API-Endpunkte für Benachrichtigungen.
 * <p>
 * Dieser Controller bietet Methoden, um Benachrichtigungen für Benutzer abzurufen
 * und zu löschen. Die Endpunkte sind unter dem Basis-Pfad "/api" verfügbar.
 * </p>
 *
 * <ul>
 *   <li>Verwendet die {@link BenachrichtigungService}, um die Geschäftslogik auszuführen.</li>
 *   <li>Unterstützt die JSON-Verarbeitung und RESTful-Methoden durch {@code @RestController}.</li>
 * </ul>
 *
 * @author Beyza Nur Acikgöz
 * @version 1.0
 */

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BenachrichtigungController {
    /**
     * Service, der die Geschäftslogik für Benachrichtigungen bereitstellt.
     */
    private final BenachrichtigungService benachrichtigungService;
    private final BenachrichtigungRepository benachrichtigungRepository;
    private final PBRepository pbRepository;

    /**
     * Ruft alle Benachrichtigungen eines bestimmten Benutzers ab.
     * <p>
     * Dieser Endpunkt liefert eine Liste von Benachrichtigungen, die mit dem Benutzernamen
     * verknüpft sind. Es wird die Methode {@link BenachrichtigungService#alleLesen(String)}
     * verwendet, um die Daten abzurufen.
     * </p>
     *
     * @param username Der Benutzername, für den die Benachrichtigungen abgerufen werden sollen.
     * @return Eine Liste von {@link Benachrichtigung} Objekten.
     */

    @GetMapping("/nachrichten/{username}")
    public List<Benachrichtigung> getBenachrichtigung(@PathVariable String username) {
        return benachrichtigungService.alleLesen(username);
    }

    /**
     * Löscht alle Benachrichtigungen eines bestimmten Benutzers.
     * <p>
     * Dieser Endpunkt entfernt alle Benachrichtigungen, die mit dem Benutzernamen verknüpft sind.
     * Wenn ein Fehler auftritt, wird eine entsprechende HTTP-Fehlermeldung zurückgegeben.
     * </p>
     *
     * @param username Der Benutzername, dessen Benachrichtigungen gelöscht werden sollen.
     * @return Eine {@link ResponseEntity}, die entweder eine Erfolgsmeldung oder
     *         eine Fehlermeldung mit entsprechendem HTTP-Status zurückgibt.
     */

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

    @PostMapping("/arbeitstageNachricht")
    public ResponseEntity<String> arbeitstageNachricht(@RequestBody Benachrichtigung benachrichtigung) {
        try{
            //Nachricht an Studentin
            benachrichtigungRepository.save(benachrichtigung);
            //Nachricht an PB
            Praktikumsbeauftragter pb = pbRepository.findByUserRole(AppUserRole.PRAKTIKUMSBEAUFTRAGTER)
                    .orElseThrow(() -> new IllegalArgumentException("Kein Praktikumsbeauftragter mit der Rolle ADMIN gefunden."));

            Benachrichtigung neueBenachrichtigung = new Benachrichtigung(
                    benachrichtigung.getEmpfaenger() + ": " + benachrichtigung.getNachricht(),
                    new Date(),
                    pb.getUsername()
            );
            benachrichtigungRepository.save(neueBenachrichtigung);

            return ResponseEntity.ok("Absolvierte Arbeitstage wurde an Studentin und PB übermittelt.");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ein unerwarteter Fehler ist aufgetreten.");
        }
    }
}
