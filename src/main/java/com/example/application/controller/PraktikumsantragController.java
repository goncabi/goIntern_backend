package com.example.application.controller;

import com.example.application.models.Praktikumsantrag;
import com.example.application.services.PraktikumsantragService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController // wandelt Rückgabewerte von Methoden automatisch in JSON um, damit sie über HTTP verwendet werden können.
@RequestMapping("/api/antrag")//legt die Basis-URL für alle Endpunkte fest.



public class PraktikumsantragController {

    private final PraktikumsantragService praktikumsantragService;


    @PostMapping("/speichern")
    public ResponseEntity<EntityModel<Praktikumsantrag>> speichernAntrag(@RequestBody Praktikumsantrag antrag) {
        //Das EntityModel von Spring HATEOAS wird verwendet, um Links in der Antwort einzubinden.
        //Das Objekt gespeicherterAntrag wird in ein EntityModel eingebettet.
        try {
            if(praktikumsantragService.antragVorhanden(antrag.getMatrikelnummer())) {
                return ResponseEntity.badRequest()
                                     .build();
            }

            praktikumsantragService.antragSpeichern(antrag);

            EntityModel<Praktikumsantrag> resource = EntityModel.of(antrag, linkTo(methodOn(PraktikumsantragController.class).speichernAntrag(antrag)).withSelfRel(),
                    //withSelfRel: Erstellt einen Link zur eigenen Methode speichern mit der gleichen Ressource.
                    linkTo(methodOn(PraktikumsantragController.class).getAlleAntraege()).withRel("alle-antraege")
                    //withRel("alle-antraege"): Fügt einen generischen Link hinzu, um alle Praktikumsantrag-Einträge aufzulisten.
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(resource);
            //Gibt einen HTTP-Status 201 (CREATED) mit der URI des neu erstellten Ressourcenlinks (SELF-Link) zurück.

        }
        catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                                 .body(null);
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(null);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@PathVariable String matrikelnummer, @RequestBody Praktikumsantrag antrag) {

        try {
            praktikumsantragService.antragBearbeiten(matrikelnummer, antrag);
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Antrag konnte nicht bearbeitet werden" + e.getMessage());
        }
        return ResponseEntity.ok("Antrag wurde erfolgreich bearbeitet!");
    }

    //Daten aus der Datenbank löschen mit DeleteMapping

    @DeleteMapping("/{matrikelnummer}")
    public ResponseEntity<String> antragLoeschen(@PathVariable String matrikelnummer) {
        try {
            praktikumsantragService.antragLoeschen(matrikelnummer);
            return ResponseEntity.ok("Praktikumsantrag wurde erfolgreich gelöscht.");
        }

        catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Fehler beim Löschen des Antrags: " + e.getMessage());
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Ein unerwarteter Fehler ist aufgetreten.");
        }
    }

    @PostMapping("/uebermitteln/")
    public ResponseEntity<String> uebermittelnAntrag(@Valid @RequestBody Praktikumsantrag antrag) { //mit @Valid werden die Angaben validiert.
        try {
            praktikumsantragService.antragUebermitteln(antrag);
            return ResponseEntity.ok("Antrag erfolgreich übermittelt.");
        }
        catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                                 .body("Fehler beim Übermitteln des Antrags: " + e.getMessage());
        }
        catch(ConstraintViolationException e) {
            return ResponseEntity.badRequest()
                                 .body("Datenvalidierung fehlgeschlagen: " + e.getMessage());
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Ein unerwarteter Fehler ist aufgetreten.");
        }
    }

    @GetMapping("/alle")
    public ResponseEntity<List<Praktikumsantrag>> getAlleAntraege() {
        List<Praktikumsantrag> antraege = praktikumsantragService.getAllAntraege();
        return ResponseEntity.ok(antraege);
    }

}

