package com.example.application.services;

import com.example.application.models.Praktikumsbeauftragter;
import com.example.application.repositories.PBRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PBServiceTest {

    @Mock
    private PBRepository pBRepository; // Mock für das Repository

    @InjectMocks
    private PBService pbService; // pbService mit gemockten Repositories injizieren

    private Praktikumsbeauftragter praktikumsbeauftragter;

    @BeforeEach
    void setUp() {
        // Praktikumsbeauftragter erstellen
        praktikumsbeauftragter = new Praktikumsbeauftragter();
        praktikumsbeauftragter.setPasswort("AbInDieFreiheit13579!");
        praktikumsbeauftragter.setUsername("Jörn Freiheit");
    }

    @Test
    void testSignUpPBUserDoesNotExist() {
        // Mock-Verhalten definieren: Benutzername existiert nicht: empty()
        Mockito.when(pBRepository.findByUsername(praktikumsbeauftragter.getUsername()))
                .thenReturn(Optional.empty());

        // Methode testen
        pbService.signUpUser(praktikumsbeauftragter);

        // Überprüfen, dass der Benutzer gespeichert wurde (save wird genau 1x aufgeufen)
        verify(pBRepository, times(1)).save(praktikumsbeauftragter);
    }

    @Test
    void testSignUpPBUserExists() {
        praktikumsbeauftragter.setUsername("Jörn Freiheit");

        // Mock-Verhalten definieren: findByUsername gibt bereits existierenden Benutzer zurück
        Mockito.when(pBRepository.findByUsername(praktikumsbeauftragter.getUsername()))
                .thenReturn(Optional.of(praktikumsbeauftragter));

        // Überprüfen, dass die Methode eine IllegalStateException wirft
        Assertions.assertThrows(IllegalStateException.class,
                () -> pbService.signUpUser(praktikumsbeauftragter));

        // Überprüfen, dass save nicht aufgerufen wurde (0 Mal)
        verify(pBRepository, times(0)).save(Mockito.any());
    }

}
