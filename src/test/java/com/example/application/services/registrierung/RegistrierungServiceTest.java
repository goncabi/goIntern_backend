package com.example.application.services.registrierung;

import com.example.application.models.RegistrierungsAnfrage;
import com.example.application.models.Sicherheitsantwort;
import com.example.application.models.Studentin;
import com.example.application.repositories.SicherheitsantwortRepository;
import com.example.application.services.StudentinService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.never;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RegistrierungServiceTest {

    @Mock
    private StudentinService studentinService;

    @Mock
    private SicherheitsantwortRepository sicherheitsantwortRepository;

    @Mock
    private PasswortValidierer passwortValidierer;

    @Mock
    private MatrikelnummerValidierer matrikelnummerValidierer;

    @InjectMocks
    private RegistrierungService registrierungService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistrierenMitValidenDaten() {
        // mock-Werte
        RegistrierungsAnfrage anfrage = new RegistrierungsAnfrage("s0123456", "Password123!", "Password123!", 1, "Berlin");
        when(matrikelnummerValidierer.isMatrikelnummerValid("s0123456")).thenReturn(true);
        when(passwortValidierer.passwordValidation("Password123!", "Password123!")).thenReturn(true);

        // registrieren mit Mockwerten aufrufen
        registrierungService.registrieren(anfrage);

        // mit any: Mockito prüft, ob Methode signUpUser irgendwann im Testablauf mit einem Studentin-Objekt aufgerufen wurde
        verify(studentinService).signUpUser(any(Studentin.class));
        verify(sicherheitsantwortRepository).save(any(Sicherheitsantwort.class));
    }

    @Test
    void testRegistrierenMatrikelnummerformatFalsch() {
        // mock-Werte
        RegistrierungsAnfrage anfrage = new RegistrierungsAnfrage("XXXXXX", "Password123!", "Password123!", 1, "Berlin");
        when(matrikelnummerValidierer.isMatrikelnummerValid("XXXXXX")).thenReturn(false);

        // weil Format falsch ist, wird Excpetion geworfen
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            registrierungService.registrieren(anfrage);
        });
        assertEquals("Matrikelnummer invalide.", exception.getMessage());

        //im Gegensatz zu any wird hier die Methode signUpUser nie aufgerufen und das wird getestet, weil Format falsch
        verify(studentinService, never()).signUpUser(any());
        verify(sicherheitsantwortRepository, never()).save(any());
    }

    @Test
    void testRegistrierversuchAberPasswörterNichtGleich() {

        RegistrierungsAnfrage anfrage = new RegistrierungsAnfrage("s0123456", "Password123!", "blablabla", 1, "Berlin");
        when(matrikelnummerValidierer.isMatrikelnummerValid("s0123456")).thenReturn(true);
        when(passwortValidierer.passwordValidation("Password123!", "blablabla")).thenReturn(false);

        // weil beide pw unterschiedlich
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            registrierungService.registrieren(anfrage);
        });
        assertEquals("Passwort invalide.", exception.getMessage());

        verify(studentinService, never()).signUpUser(any());
        verify(sicherheitsantwortRepository, never()).save(any());
    }

    @Test
    void testRegistrierenOhneEingabeEinerSicherheitsantwort() {

        String matrikelnummer = "s0123456";
        String passwort1 = "Password123!";
        String passwort2 = "Password123!";

        when(matrikelnummerValidierer.isMatrikelnummerValid(matrikelnummer)).thenReturn(true);
        when(passwortValidierer.passwordValidation(passwort1, passwort2)).thenReturn(true);

        //aufruf registrieren
        registrierungService.registrieren(matrikelnummer, passwort1, passwort2);

        // überprüfen - sicherheitsfrage wurde nie aufgerufen (never)
        verify(studentinService).signUpUser(any(Studentin.class));
        verify(sicherheitsantwortRepository, never()).save(any());
    }



}