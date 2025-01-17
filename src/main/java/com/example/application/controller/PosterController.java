package com.example.application.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/poster")

public class PosterController {

//    @GetMapping("/{matrikelnummer}")
//    public ResponseEntity<byte[]> getPoster(@PathVariable String matrikelnummer) {
//        try {
//            // Hier Poster abrufen
//
//            byte[] poster = ;
//
//            if (poster == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//            }
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_PDF); // Poster als PDF
//            headers.setContentDisposition(ContentDisposition.inline()
//                    .filename("poster_" + matrikelnummer + ".pdf")
//                    .build());
//
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .body(poster);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }


    //http://localhost:3000/api/poster/ablehnen/%s
    //http://localhost:3000/api/poster/genehmigen
    //weitere controller, die wir benötigen maybe


}
