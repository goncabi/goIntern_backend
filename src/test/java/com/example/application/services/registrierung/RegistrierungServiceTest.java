package com.example.application.services.registrierung;

import com.example.application.models.RegistrierungsAnfrage;
import com.example.application.models.Sicherheitsantwort;
import com.example.application.models.Studentin;
import com.example.application.repositories.SicherheitsantwortRepository;
import com.example.application.services.StudentinService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.never;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class RegistrierungServiceTest {

    @Mock
    private StudentinService studentinService;

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

    @Test
    void testRegistrierenValidenDaten() {
        // mock-Werte
        RegistrierungsAnfrage anfrage = new RegistrierungsAnfrage("s0123456", "Password123!", "Password123!", 1, "Berlin");

        // registrieren mit Mockwerten aufrufen
        registrierungService.registrieren(anfrage);

        // mit any: Mockito prüft, ob Methode signUpUser irgendwann im Testablauf mit einem Studentin-Objekt aufgerufen wurde
        verify(studentinService).signUpUser(any(Studentin.class));
        verify(sicherheitsantwortRepository).save(any(Sicherheitsantwort.class));
    }

    @Test
    void testRegistrierenPasswoerterNichtGleich() {

        RegistrierungsAnfrage anfrage = new RegistrierungsAnfrage("s0123456", "Password123!", "blablabla", 1, "Berlin");

        // weil beide pw unterschiedlich
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> registrierungService.registrieren(anfrage));
        assertEquals("Eingegebene Passwörter sind nicht identisch.", exception.getMessage());

        verify(studentinService, never()).signUpUser(any());
        verify(sicherheitsantwortRepository, never()).save(any());
    }

    //Null-Werte werden in Vaadin geprüft
}