package com.example.application.services;

import com.example.application.models.PasswortVergessenAnfrage;
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
    void passwortVergessenTest1() {
        PasswortVergessenAnfrage anfrage = new PasswortVergessenAnfrage("1234567", 1, "Meier");

        when(studentinRepository.findByMatrikelnummer(anfrage.getMatrikelnummer()))
                .thenReturn(Optional.empty());

        IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> passwortVergessenService.passwortVergessen(anfrage)
        );
    }
}