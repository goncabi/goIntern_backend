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

        String result = passwortVergessenService.getSicherheitsfrage(matrikelnummer);

        assertEquals(sicherheitsfrage.getFrage(), result);
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
    void testResetPasswordSuccess() {
        // Arrange
        String matrikelnummer = "12345";
        String enteredAnswer = "correctAnswer";
        String passwort = "newPassword";

        Sicherheitsantwort sicherheitsantwort = new Sicherheitsantwort();
        sicherheitsantwort.setAntwort("correctAnswer");

        Studentin studentin = new Studentin();
        studentin.setMatrikelnummer(matrikelnummer);

        when(sicherheitsantwortRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(sicherheitsantwort));
        when(studentinRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(studentin));

        // Act
        boolean result = passwortVergessenService.resetPassword(matrikelnummer, enteredAnswer, passwort);

        // Assert
        assertTrue(result);
        verify(studentinRepository).save(studentin);
        assertEquals(passwort, studentin.getPassword());
    }

    @Test
    void testResetPasswordIncorrectAnswer() {
        // Arrange
        String matrikelnummer = "12345";
        String enteredAnswer = "wrongAnswer";
        String passwort = "newPassword";

        Sicherheitsantwort sicherheitsantwort = new Sicherheitsantwort();
        sicherheitsantwort.setAntwort("correctAnswer");

        when(sicherheitsantwortRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(sicherheitsantwort));

        // Act
        boolean result = passwortVergessenService.resetPassword(matrikelnummer, enteredAnswer, passwort);

        // Assert
        assertFalse(result);
        verify(studentinRepository, never()).save(any());
    }

    @Test
    void testResetPasswordSicherheitsantwortNotFound() {
        // Arrange
        String matrikelnummer = "12345";
        String enteredAnswer = "anyAnswer";
        String passwort = "newPassword";

        when(sicherheitsantwortRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                passwortVergessenService.resetPassword(matrikelnummer, enteredAnswer, passwort)
        );
        assertEquals("Fehler beim Finden der Sicherheitsantwort.", exception.getMessage());
    }

    @Test
    void testResetPasswordStudentinNotFound() {
        // Arrange
        String matrikelnummer = "12345";
        String enteredAnswer = "correctAnswer";
        String passwort = "newPassword";

        Sicherheitsantwort sicherheitsantwort = new Sicherheitsantwort();
        sicherheitsantwort.setAntwort("correctAnswer");

        when(sicherheitsantwortRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(sicherheitsantwort));
        when(studentinRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                passwortVergessenService.resetPassword(matrikelnummer, enteredAnswer, passwort)
        );
        assertEquals("Fehler beim Finden der Studentin.", exception.getMessage());
    }
}