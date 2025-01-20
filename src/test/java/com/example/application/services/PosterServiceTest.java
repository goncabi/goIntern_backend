package com.example.application.services;

import com.example.application.models.Poster;
import com.example.application.repositories.PosterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PosterServiceTest {
    @Mock
    private PosterRepository posterRepository;

    @Mock
    private PBService pbService;

    @InjectMocks
    private PosterService posterService;

    private MockMultipartFile validFile;
    private MockMultipartFile invalidFile;

    @BeforeEach
    void setUp() {
        validFile = new MockMultipartFile(
                "file", "poster.pdf", "application/pdf", "dummy content".getBytes());
        invalidFile = new MockMultipartFile(
                "file", "poster.txt", "text/plain", "dummy content".getBytes());
    }

    @Test
    void testSavePoster_WithValidPdf_ShouldSaveSuccessfully() throws IOException {
        String matrikelnummer = "123456";
        posterService.savePoster(validFile, matrikelnummer);

        verify(posterRepository, times(1)).save(any(Poster.class));
        verify(pbService, times(1)).posterNachrichtUebermitteln(matrikelnummer);
    }

    @Test
    void testSavePoster_WithNullFile_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> posterService.savePoster(null, "123456"));
        assertEquals("File is empty", exception.getMessage());
    }

    @Test
    void testSavePoster_WithEmptyFile_ShouldThrowException() {
        MockMultipartFile emptyFile = new MockMultipartFile("file", "", "application/pdf", new byte[0]);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> posterService.savePoster(emptyFile, "123456"));
        assertEquals("File is empty", exception.getMessage());
    }

    @Test
    void testSavePoster_WithInvalidFileType_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> posterService.savePoster(invalidFile, "123456"));
        assertEquals("Nur PDFs erlaubt!", exception.getMessage());
    }

    @Test
    void testGetPoster_WithExistingMatrikelnummer_ShouldReturnPoster() {
        String matrikelnummer = "123456";
        Poster mockPoster = new Poster(matrikelnummer, "poster.pdf", new byte[0]);
        when(posterRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(mockPoster));

        Poster result = posterService.getPoster(matrikelnummer);

        assertNotNull(result);
        assertEquals(matrikelnummer, result.getMatrikelnummer());
    }

    @Test
    void testGetPoster_WithNonExistingMatrikelnummer_ShouldThrowException() {
        String matrikelnummer = "123456";
        when(posterRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> posterService.getPoster(matrikelnummer));
        assertEquals("Poster nicht gefunden", exception.getMessage());
    }
}