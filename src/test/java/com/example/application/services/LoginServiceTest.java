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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    void loginStudent_shouldReturnTrue_whenCredentialsAreCorrect() {
        String matrikelnummer = "s0123456";
        String password = "password1!";
        Studentin studentin = new Studentin(matrikelnummer, password, AppUserRole.STUDENTIN);
        LoginAnfrage loginAnfrage = new LoginAnfrage("Student/in", matrikelnummer, password);

        when(studentinRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(studentin));

        boolean result = loginService.login(loginAnfrage);

        assertTrue(result);
        verify(studentinRepository, times(2)).findByMatrikelnummer(matrikelnummer);
    }

    @Test
    void loginStudent_shouldReturnFalse_whenPasswordIsIncorrect() {
        String matrikelnummer = "s0123456";
        String correctPassword = "correctPassword";
        String wrongPassword = "wrongPassword";
        Studentin studentin = new Studentin(matrikelnummer, correctPassword, AppUserRole.STUDENTIN);
        LoginAnfrage loginAnfrage = new LoginAnfrage("Student/in", matrikelnummer, wrongPassword);

        when(studentinRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(studentin));

        boolean result = loginService.login(loginAnfrage);

        assertFalse(result);
        verify(studentinRepository, times(2)).findByMatrikelnummer(matrikelnummer);
    }

    @Test
    void loginStudent_shouldReturnFalse_whenMatrikelnummerDoesNotExist() {
        String matrikelnummer = "s0123456";
        String password = "password";
        LoginAnfrage loginAnfrage = new LoginAnfrage("Student/in", matrikelnummer, password);

        when(studentinRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.empty());

        boolean result = loginService.login(loginAnfrage);

        assertFalse(result);
        verify(studentinRepository, times(1)).findByMatrikelnummer(matrikelnummer);
    }

    @Test
    void loginPB_ReturnTrueWennAlleLoginDatenRichtigSind() {
        String username = "Praktikumsbeauftragter";
        String password = "PB_Passwort";
        Praktikumsbeauftragter pb = new Praktikumsbeauftragter(username, password, AppUserRole.PRAKTIKUMSBEAUFTRAGTER);
        LoginAnfrage loginAnfrage = new LoginAnfrage("Praktikumsbeauftragte/r", username, password);

        when(pbRepository.findByUsername(username)).thenReturn(Optional.of(pb));

        boolean result = loginService.login(loginAnfrage);
        assertTrue(result);
        verify(pbRepository, times(2)).findByUsername(username);
    }

    @Test
    void loginPB_ReturnFalseWennLoginDatenFalschSind() {
        String username = "Praktikumsbeauftragter";
        String correctPassword = "Richtiges Passwort";
        String wrongPassword = "Falsches Passwort";
        Praktikumsbeauftragter pb = new Praktikumsbeauftragter(username, correctPassword, AppUserRole.PRAKTIKUMSBEAUFTRAGTER);
        LoginAnfrage loginAnfrage = new LoginAnfrage("Praktikumsbeauftragte/r", username, wrongPassword);

        when(pbRepository.findByUsername(username)).thenReturn(Optional.of(pb));

        boolean result = loginService.login(loginAnfrage);

        assertFalse(result);
        verify(pbRepository, times(2)).findByUsername(username);
    }

    @Test
    void loginPB_ReturnFalse_WennUserNichtExistiert() {
        String username = "Nicht vorhandener UserName";
        String password = "irgendeinPasswort";
        LoginAnfrage loginAnfrage = new LoginAnfrage("Praktikumsbeauftragte/r", username, password);

        when(pbRepository.findByUsername(username)).thenReturn(Optional.empty());

        boolean result = loginService.login(loginAnfrage);
        assertFalse(result);
    }
}

