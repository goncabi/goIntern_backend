package com.example.application.services;

import com.example.application.models.*;
import com.example.application.repositories.SicherheitsantwortRepository;
import com.example.application.repositories.SicherheitsfrageRepository;
import com.example.application.repositories.StudentinRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional


class PasswortVergessenServiceTest {

    @MockBean
    private SicherheitsantwortRepository sicherheitsantwortRepository;

    @MockBean
    private SicherheitsfrageRepository sicherheitsfrageRepository;

    @MockBean
    private StudentinRepository studentinRepository;

    @Autowired
    private PasswortVergessenService passwortVergessenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEingabeMatrikelnummer_Exists() {
        String matrikelnummer = "123456";
        when(studentinRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(new Studentin()));

        boolean result = passwortVergessenService.eingabeMatrikelnummer(matrikelnummer);

        assertTrue(result);
        verify(studentinRepository, times(1)).findByMatrikelnummer(matrikelnummer);
    }

    @Test
    void testEingabeMatrikelnummer_NotExists() {
        String matrikelnummer = "654321";
        when(studentinRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.empty());

        boolean result = passwortVergessenService.eingabeMatrikelnummer(matrikelnummer);

        assertFalse(result);
        verify(studentinRepository, times(1)).findByMatrikelnummer(matrikelnummer);
    }

    @Test
    void testAusgabeFrage_Exists() {
        String matrikelnummer = "123456";
        Sicherheitsfrage sicherheitsfrage = new Sicherheitsfrage(1, "hallo?");
        when(sicherheitsantwortRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(new Sicherheitsantwort()));
        when(sicherheitsfrageRepository.findById(anyLong())).thenReturn(Optional.of(sicherheitsfrage));

        Sicherheitsfrage result = passwortVergessenService.getSicherheitsfrage(matrikelnummer);

        assertEquals(sicherheitsfrage, result);
        verify(sicherheitsantwortRepository, times(2)).findByMatrikelnummer(matrikelnummer);
        verify(sicherheitsfrageRepository, times(2)).findById(anyLong());
    }

    @Test
    void testAusgabeFrage_AntwortNotExists() {
        String matrikelnummer = "123456";
        when(sicherheitsantwortRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> passwortVergessenService.getSicherheitsfrage("123456"));

        assertEquals("Fehler beim Finden der Sicherheitsantwort.", exception.getMessage());
        verify(sicherheitsantwortRepository, times(1)).findByMatrikelnummer(matrikelnummer);
    }

    @Test
    void testAusgabeFrage_FrageNotExists() {
        String matrikelnummer = "123456";
        when(sicherheitsantwortRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(new Sicherheitsantwort()));
        when(sicherheitsfrageRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> passwortVergessenService.getSicherheitsfrage("123456"));

        assertEquals("Fehler beim Finden der Sicherheitsfrage.", exception.getMessage());
        verify(sicherheitsantwortRepository, times(2)).findByMatrikelnummer(matrikelnummer);
        verify(sicherheitsfrageRepository, times(1)).findById(anyLong());
    }

    @Test
    void testEingabeAntwort_AntwortRichtig(){
        String matrikelnummer = "123456";
        Sicherheitsantwort antwort = new Sicherheitsantwort(1, matrikelnummer, "hallo");
        when(sicherheitsantwortRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(antwort));

        boolean result = passwortVergessenService.eingabeAntwort(matrikelnummer, "hallo");

        assertTrue(result);
        verify(sicherheitsantwortRepository, times(1)).findByMatrikelnummer(matrikelnummer);
    }

    @Test
    void testEingabeAntwort_AntwortFalsch(){
        String matrikelnummer = "123456";
        Sicherheitsantwort antwort = new Sicherheitsantwort(1, matrikelnummer, "hallo");
        when(sicherheitsantwortRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(antwort));

        boolean result = passwortVergessenService.eingabeAntwort(matrikelnummer, "falsch");

        assertFalse(result);
        verify(sicherheitsantwortRepository, times(1)).findByMatrikelnummer(matrikelnummer);
    }

    @Test
    void testAusgabePasswort_StudentinGefundenPasswoerterIdentisch(){
        String matrikelnummer = "123456";
        String passwortNeu = "id834#";
        when(studentinRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(new Studentin()));

        String result = passwortVergessenService.neuesPasswortSetzen(matrikelnummer, passwortNeu, passwortNeu);

        assertEquals("Neues Passwort wurde erfolgreich gesetzt.", result);
        verify(studentinRepository, times(2)).findByMatrikelnummer(matrikelnummer);
        verify(studentinRepository, times(1)).save(any(Studentin.class));
    }

    @Test
    void testAusgabePasswort_StudentinGefundenPasswoerterNichtIdentisch(){
        String matrikelnummer = "123456";
        String passwortNeu = "id834#";
        String passwortWdh = "id8u";
        when(studentinRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(new Studentin()));

        String result = passwortVergessenService.neuesPasswortSetzen(matrikelnummer, passwortNeu, passwortWdh);

        assertEquals("Passwörter sind nicht identisch.", result);
        verify(studentinRepository, times(1)).findByMatrikelnummer(matrikelnummer);
        verify(studentinRepository, times(0)).save(any(Studentin.class));
    }

    @Test
    void testAusgabePasswort_StudentinEmpty(){
        String matrikelnummer = "123456";
        String passwortNeu = "id834#";
        when(studentinRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> passwortVergessenService.neuesPasswortSetzen(matrikelnummer, passwortNeu, passwortNeu));

        assertEquals("Fehler beim Finden der Studentin.", exception.getMessage());
        verify(studentinRepository, times(1)).findByMatrikelnummer(matrikelnummer);
    }
}