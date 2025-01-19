package com.example.application.controller;

import com.example.application.models.Poster;
import com.example.application.services.PosterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/poster")
@AllArgsConstructor
public class PosterController {

    private final PosterService posterService;

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

    @GetMapping("/pdf/{matrikelnummer}")
    public ResponseEntity<byte[]> getPdf(@PathVariable String matrikelnummer) {
        try{
            Poster poster = posterService.getPoster(matrikelnummer);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(poster.getPosterPDF());
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("infos/{matrikelnummer}")
    public ResponseEntity<Poster> posterInfos(@PathVariable String matrikelnummer) {
        try{
            Poster poster = posterService.getPoster(matrikelnummer);
            return ResponseEntity.ok(poster);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
