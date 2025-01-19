package com.example.application.services;

import com.example.application.models.Poster;
import com.example.application.models.StatusPoster;
import com.example.application.repositories.PosterRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PosterService {

    private final PosterRepository posterRepository;
    private final PBService pbService;

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
        poster.setStatus(StatusPoster.EINGEREICHT);
        posterRepository.save(poster);
        pbService.posterNachrichtUebermitteln(matrikelnummer);
    }

    @Transactional
    public Poster getPoster(String matrikelnummer) {
        return posterRepository.findByMatrikelnummer(matrikelnummer)
                .orElseThrow(() -> new RuntimeException("Poster nicht gefunden"));
    }
}
