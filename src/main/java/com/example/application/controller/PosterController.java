package com.example.application.controller;

import com.example.application.services.PosterService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/poster")

public class PosterController {

    //Objektvaribale für PosterService
    private PosterService posterService;

    @GetMapping("/{matrikelnummer}")
    //Annotation für die methode. Wenn der Endpunkt aufgerufen wird, wird die Methode getPoster aufgerufen.
    public ResponseEntity<byte[]> getPoster(@PathVariable String matrikelnummer) {
        try {
            // Hier Poster abrufen

            byte[] poster = posterService.posterAbrufen(matrikelnummer);

             if (poster == null) {
                  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF); // Poster als PDF
            headers.setContentDisposition(ContentDisposition.inline()
                    .filename("poster_" + matrikelnummer + ".pdf")
                    .build());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(poster);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Poster speichern Methode: (Path Variable heißt dass in dem Pfad eine PathVariable drin ist)
    // Beispielweise: localhost:3000/api/poster/s0190191
    @PostMapping("/{matrikelnummer}")
    public ResponseEntity<String> posterSpeichern(@PathVariable String matrikelnummer, @RequestParam MultipartFile poster) {
        try{
            //Poster Service speichert die Datei
            posterService.posterSpeichern(poster, matrikelnummer);
        }
     catch (Exception e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
       return ResponseEntity.status(HttpStatus.OK).build();
    }

    // http://localhost:3000/api/poster/ablehnen/%s
    // http://localhost:3000/api/poster/genehmigen
    //weitere controller, die wir benötigen maybe


}
