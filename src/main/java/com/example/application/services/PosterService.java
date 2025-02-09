package com.example.application.services;

import com.example.application.models.Poster;
import com.example.application.repositories.PosterRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Objects;

/**
 * Service-Klasse zur Verwaltung von Postern.
 * Diese Klasse stellt Methoden zum Speichern und Abrufen von Postern bereit.
 */
@Service
@AllArgsConstructor
public class PosterService {

    private final PosterRepository posterRepository;
    private final PBService pbService;

    /**
     * Speichert ein Poster und sendet dem PB eine Nachricht.
     * @param file die hochgeladene Datei
     * @param matrikelnummer die Matrikelnummer der Studentin
     * @throws IOException falls ein Fehler beim Speichern der Datei auftritt
     * @throws IllegalArgumentException falls die Datei leer ist oder kein PDF-Format hat
     */
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
        pbService.posterNachrichtUebermitteln(matrikelnummer);
    }

    /**
     * Ruft ein Poster anhand der Matrikelnummer ab.
     * Transactional (transaktionale Verbindung) muss verwendet werden, um den LOB aufzurufen.
     *
     * @param matrikelnummer die Matrikelnummer der Studentin
     * @return das gefundene Poster
     * @throws RuntimeException falls kein Poster mit der angegebenen Matrikelnummer gefunden wird
     */
    @Transactional
    public Poster getPoster(String matrikelnummer) {
        return posterRepository.findByMatrikelnummer(matrikelnummer)
                .orElseThrow(() -> new RuntimeException("Poster nicht gefunden"));
    }

    public boolean isPosterAvailable(String matrikelnummer) {
        return posterRepository.existsByMatrikelnummer(matrikelnummer);
    }


}
