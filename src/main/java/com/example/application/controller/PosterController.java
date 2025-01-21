package com.example.application.controller;

import com.example.application.models.Poster;
import com.example.application.services.PosterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Der PosterController stellt Rest-Endpunkte zur Verfügung,
 * die den Upload und Download von Pdf-Dokumenten ermöglichen.
 * Dieser Controller ist mit dem Frontend über die Route ("/poster") verknüpft.
 */
@RestController
@RequestMapping("/api/poster")
@AllArgsConstructor
public class PosterController {

    private final PosterService posterService;

    /**
     * Verarbeitet die Upload-Anfrage einer Studentin.
     * @param file die hochgeladene Datei
     * @param matrikelnummer die Matrikelnummer der Studentin
     * @return ResponseEntity mit einer Erfolgsmeldung oder einer Fehlermeldung
     */
    @PostMapping("/upload/{matrikelnummer}")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file, @PathVariable String matrikelnummer) {
        try{
            posterService.savePoster(file, matrikelnummer);
            return ResponseEntity.ok("Poster erfolgreich gespeichert.");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler beim Speichern der Datei.");
        }
    }

    /**
     * Ruft das gespeicherte Poster-PDF anhand der Matrikelnummer ab.
     * @param matrikelnummer Matrikelnummer der Studentin
     * @return ResponseEntity mit der PDF-Datei oder eine Fehlermeldung
     */
    @GetMapping("/pdf/{matrikelnummer}")
    public ResponseEntity<byte[]> getPdf(@PathVariable String matrikelnummer) {
        try{
            Poster poster = posterService.getPoster(matrikelnummer);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + poster.getFileName() + "\"")
                    .body(poster.getPosterPDF());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Ruft den Status des Posters anhand der Matrikelnummer ab.
     * @param matrikelnummer Matrikelnummer der Studentin
     * @return ResponseEntity mit dem Status des Posters oder einer Fehlermeldung
     */
    @GetMapping("status/{matrikelnummer}")
    public ResponseEntity<String> getStatus(@PathVariable String matrikelnummer) {
        try{
            Poster poster = posterService.getPoster(matrikelnummer);
            String status = poster.getStatus().toString();
            return ResponseEntity.ok(status);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Fehler beim Abrufen der Datei.");
        }
    }

}
