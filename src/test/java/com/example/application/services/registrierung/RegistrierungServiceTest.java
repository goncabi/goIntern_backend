package com.example.application.services.registrierung;

import com.example.application.models.RegistrierungsAnfrage;
import com.example.application.models.Sicherheitsantwort;
import com.example.application.models.Studentin;
import com.example.application.repositories.SicherheitsantwortRepository;
import com.example.application.repositories.StudentinRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegistrierungServiceTest {

    @Mock
    private SicherheitsantwortRepository sicherheitsantwortRepository;

    @InjectMocks
    private RegistrierungService registrierungService;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    @Mock
    private StudentinRepository studentinRepository;


        @Test
        void testRegistrieren_PasswoerterNichtIdentisch() {
            RegistrierungsAnfrage anfrage = new RegistrierungsAnfrage("user1", "password", "password123", "1", "Antwort");

            boolean result = registrierungService.registrieren(anfrage);

            assertFalse(result, "Die Registrierung sollte fehlschlagen, wenn die Passwörter nicht übereinstimmen.");
            verifyNoInteractions(studentinRepository);
            verifyNoInteractions(sicherheitsantwortRepository);
        }

        @Test
        void testRegistrieren_BenutzernameBereitsVorhanden() {
            RegistrierungsAnfrage anfrage = new RegistrierungsAnfrage("user1", "password", "password", "1", "Antwort");
            when(studentinRepository.findByMatrikelnummer("user1")).thenReturn(Optional.of(new Studentin()));

            boolean result = registrierungService.registrieren(anfrage);

            assertFalse(result, "Die Registrierung sollte fehlschlagen, wenn der Benutzername bereits existiert.");
            verify(studentinRepository, times(1)).findByMatrikelnummer("user1");
            verifyNoInteractions(sicherheitsantwortRepository);
        }

        @Test
        void testRegistrieren_ErfolgreicheRegistrierung() {
            RegistrierungsAnfrage anfrage = new RegistrierungsAnfrage("user1", "password", "password", "1", "Antwort");
            when(studentinRepository.findByMatrikelnummer("user1")).thenReturn(Optional.empty());

            boolean result = registrierungService.registrieren(anfrage);

            assertTrue(result, "Die Registrierung sollte erfolgreich sein.");
            verify(studentinRepository, times(1)).findByMatrikelnummer("user1");
            verify(studentinRepository, times(1)).save(any(Studentin.class));
            verify(sicherheitsantwortRepository, times(1)).save(any(Sicherheitsantwort.class));
        }

}