package com.example.application.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class PosterService {

    //Objektvariable:
    private PBService pbService;

    //Methode die das Poster speichert
    public void posterSpeichern(MultipartFile poster, String matrikelnummer){
       pbService.posterNachrichtUebermitteln(matrikelnummer);
    }

    //Methode ruft das Poster aus der Datenbank ab und gibt es zurück:
    public byte[] posterAbrufen(String matrikelnummer) {
        return null;
    }


}
