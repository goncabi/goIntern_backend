package com.example.application.services;

import com.example.application.models.AppUserRole;
import com.example.application.models.PasswortVergessenAnfrage;
import com.example.application.models.Sicherheitsantwort;
import com.example.application.models.Studentin;
import com.example.application.repositories.SicherheitsantwortRepository;
import com.example.application.repositories.StudentinRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PasswortVergessenServiceTest {

    //zwei Mocks:
    @MockBean // MockBean ist dafür da um die Datenbank nicht nutzen zu muessen
    private SicherheitsantwortRepository sicherheitsantwortRepository;
    @MockBean
    private StudentinRepository studentinRepository;

    @Autowired
    private PasswortVergessenService passwortVergessenService;

    @Test
    void passwortVergessenTestObeineExceptionGeworfen() {
        PasswortVergessenAnfrage anfrage = new PasswortVergessenAnfrage("1234567", 1, "Berlin");

        when(studentinRepository.findByMatrikelnummer(anfrage.getMatrikelnummer()))
                .thenReturn(Optional.empty());

        IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> passwortVergessenService.passwortVergessen(anfrage)
        );
    }

    @Test
    void passwortVergessenTestObEingabeRichtig() {
        PasswortVergessenAnfrage anfrage = new PasswortVergessenAnfrage("1234567", 1, "Berlin");

        when(studentinRepository.findByMatrikelnummer(anfrage.getMatrikelnummer()))
                .thenReturn(Optional.of(new Studentin("45678", "passwort1", AppUserRole.USER)));

        when(sicherheitsantwortRepository.findByMatrikelnummer(anfrage.getMatrikelnummer()))
                .thenReturn(Optional.of(new Sicherheitsantwort(1, "1234567", "Berlin")));
        assertTrue(passwortVergessenService.passwortVergessen(anfrage));
    }


    @Test
    void passwortVergessenTestObEingabeFalsch() {
        PasswortVergessenAnfrage anfrage = new PasswortVergessenAnfrage("1234567", 1, "Berlin");

        when(studentinRepository.findByMatrikelnummer(anfrage.getMatrikelnummer()))
                .thenReturn(Optional.of(new Studentin("45678", "passwort1", AppUserRole.USER)));

        when(sicherheitsantwortRepository.findByMatrikelnummer(anfrage.getMatrikelnummer()))
                .thenReturn(Optional.of(new Sicherheitsantwort(1, "1234567", "Kassel")));
        assertFalse(passwortVergessenService.passwortVergessen(anfrage));
    }
}