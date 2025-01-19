package com.example.application.services;

import com.example.application.models.Poster;
import com.example.application.repositories.PosterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PosterService {

    private final PosterRepository posterRepository;

    public void savePoster(MultipartFile file, String matrikelnummer) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".pdf")) {
            throw new IllegalArgumentException("Nur PDFs erlaubt!");
        }

        Poster poster = new Poster(
                matrikelnummer,
                file.getOriginalFilename(),
                file.getBytes()
        );
        posterRepository.save(poster);
    }

    public Poster getPoster(String matrikelnummer) throws Exception {
        if(posterRepository.findByMatrikelnummer(matrikelnummer).isPresent()) {
            return posterRepository.findByMatrikelnummer(matrikelnummer).get();
        }
        else{
            throw new Exception("Fehler beim Finden des Posters.");
        }
    }
}
