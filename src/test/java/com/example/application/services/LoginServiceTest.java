package com.example.application.services;

import com.example.application.models.AppUserRole;
import com.example.application.models.LoginAnfrage;
import com.example.application.models.Praktikumsbeauftragter;
import com.example.application.models.Studentin;
import com.example.application.repositories.PBRepository;
import com.example.application.repositories.StudentinRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class LoginServiceTest {

    @MockBean
    private StudentinRepository studentinRepository;

    @MockBean
    private PBRepository pbRepository;

    @Autowired//erstellt eine Abhängigkeit damit eine Objektvariable loginService erstellt wird. Und es werden alle Abhängikeiten erstellt, auch studentinRepository und praktikumsbeauftragterRepository
    private LoginService loginService;

    @Test
    void loginPB_shouldReturnMatrikelnummer_whenCredentialsAreCorrect() {
        String username = "PRAKTIKUMSBEAUFTRAGTER";
        String password = "PB_Passwort";
        Praktikumsbeauftragter pb = new Praktikumsbeauftragter(username, password, AppUserRole.PRAKTIKUMSBEAUFTRAGTER);
        LoginAnfrage loginAnfrage = new LoginAnfrage("Praktikumsbeauftragte/r", username, password);

        when(pbRepository.findByUsername(username)).thenReturn(Optional.of(pb));

        Optional<String> result = loginService.login(loginAnfrage);

        // Erwartung: Das Optional enthält den Benutzernamen
        assertTrue(result.isPresent());
        assertEquals(username, result.get());
        verify(pbRepository, times(1)).findByUsername(username);
    }

    @Test
    void loginStudent_shouldReturnEmptyOptional_whenPasswordIsIncorrect() {
        String matrikelnummer = "s0123456";
        String correctPassword = "correctPassword";
        String wrongPassword = "wrongPassword";
        Studentin studentin = new Studentin(matrikelnummer, correctPassword, AppUserRole.STUDENTIN);
        LoginAnfrage loginAnfrage = new LoginAnfrage("Studentin", matrikelnummer, wrongPassword);

        when(studentinRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(studentin));

        Optional<String> result = loginService.login(loginAnfrage);

        assertTrue(result.isEmpty());
        verify(studentinRepository, times(1)).findByMatrikelnummer(matrikelnummer);
    }

    @Test
    void loginStudent_shouldReturnEmptyOptional_whenMatrikelnummerDoesNotExist() {
        String matrikelnummer = "s0123456";
        String password = "password";
        LoginAnfrage loginAnfrage = new LoginAnfrage("Studentin", matrikelnummer, password);

        when(studentinRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.empty());

        Optional<String> result = loginService.login(loginAnfrage);

        assertTrue(result.isEmpty());
        verify(studentinRepository, times(1)).findByMatrikelnummer(matrikelnummer);
    }

    @Test
    void loginPB_shouldReturnOptional_whenCredentialsAreCorrect() {
        String username = "Praktikumsbeauftragter";
        String password = "PB_Passwort";
        Praktikumsbeauftragter pb = new Praktikumsbeauftragter(username, password, AppUserRole.PRAKTIKUMSBEAUFTRAGTER);
        LoginAnfrage loginAnfrage = new LoginAnfrage("Praktikumsbeauftragte/r", username, password);

        when(pbRepository.findByUsername(username)).thenReturn(Optional.of(pb));

        Optional<String> result = loginService.login(loginAnfrage);

        assertTrue(result.isPresent());
        assertEquals("PRAKTIKUMSBEAUFTRAGTER", result.get());
        verify(pbRepository, times(1)).findByUsername(username);
    }



    @Test
    void loginPB_shouldReturnEmptyOptional_whenPasswordIsIncorrect() {
        String username = "Praktikumsbeauftragter";
        String correctPassword = "RichtigesPasswort";
        String wrongPassword = "FalschesPasswort";
        Praktikumsbeauftragter pb = new Praktikumsbeauftragter(username, correctPassword, AppUserRole.PRAKTIKUMSBEAUFTRAGTER);
        LoginAnfrage loginAnfrage = new LoginAnfrage("Praktikumsbeauftragte/r", username, wrongPassword);

        when(pbRepository.findByUsername(username)).thenReturn(Optional.of(pb));

        Optional<String> result = loginService.login(loginAnfrage);

        assertTrue(result.isEmpty());
        verify(pbRepository, times(1)).findByUsername(username);
    }

    @Test
    void loginPB_shouldReturnEmptyOptional_whenUserDoesNotExist() {
        String username = "NonExistentUser";
        String password = "SomePassword";
        LoginAnfrage loginAnfrage = new LoginAnfrage("Praktikumsbeauftragte/r", username, password);

        when(pbRepository.findByUsername(username)).thenReturn(Optional.empty());

        Optional<String> result = loginService.login(loginAnfrage);

        assertTrue(result.isEmpty());
        verify(pbRepository, times(1)).findByUsername(username);
    }
}

