package com.example.application.services;

import com.example.application.models.LoginAnfrageStudentin;
import com.example.application.models.Studentin;
import com.example.application.repositories.PBRepository;
import com.example.application.repositories.StudentinRepository;
import org.junit.jupiter.api.Test;
import com.example.application.models.LoginAnfragePB;
import com.example.application.models.Praktikumsbeauftragter;
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
    void loginStudent_shouldReturnTrue_whenCredentialsAreCorrect() {
        // Arrange
        String matrikelnummer = "s0123456";
        String password = "password1!";
        Studentin studentin = new Studentin();
        studentin.setMatrikelnummer(matrikelnummer);
        studentin.setPassword(password);

        LoginAnfrageStudentin loginAnfrage = new LoginAnfrageStudentin(matrikelnummer, password);

        when(studentinRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(studentin));

        // Act
        boolean result = loginService.login(loginAnfrage);

        // Assert
        assertTrue(result, "Login successfull");
    }

    @Test
    void loginStudent_shouldReturnFalse_whenPasswordIsIncorrect() {

        String matrikelnummer = "s0123456";
        String correctPassword = "correctPassword";
        String wrongPassword = "wrongPassword";
        Studentin studentin = new Studentin();
        studentin.setMatrikelnummer(matrikelnummer);
        studentin.setPassword(correctPassword);

        LoginAnfrageStudentin loginAnfrage = new LoginAnfrageStudentin(matrikelnummer, wrongPassword);

        when(studentinRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(studentin));

        // Act
        boolean result = loginService.login(loginAnfrage);

        // Assert
        assertFalse(result, "Login fails, because password is incorrect");
    }

    @Test
    void loginStudent_shouldThrowException_whenMatrikelnummerDoesNotExist() {

        String matrikelnummer = "s0123456";
        String password = "password";
        LoginAnfrageStudentin loginAnfrage = new LoginAnfrageStudentin(matrikelnummer, password);

        when(studentinRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> loginService.login(loginAnfrage));
        assertEquals("Matrikelnummer falsch oder nicht registriert", exception.getMessage());
    }

    @Test
    void loginPB_ReturnTrueWennAlleLoginDatenRichtigSind() {

        String username = "Praktikumsbeauftragter";
        String password = "PB_Passwort";
        Praktikumsbeauftragter pb = new Praktikumsbeauftragter();
        pb.setUsername(username);
        pb.setPasswort(password);
        LoginAnfragePB loginAnfrage = new LoginAnfragePB(username, password);

        when(pbRepository.findByUsername(username)).thenReturn(Optional.of(pb));

        boolean result = loginService.login(loginAnfrage);
        assertTrue(result, "Login erfolgreich wenn Passwort richtig ist");
    }

    @Test
    void loginPB_ReturntFalseWennLoginDatenFalschSind() {
        String username = "Praktikumsbeauftragter";
        String correctPassword = "Richtiges Passwort";
        String wrongPassword = "Falsches Passwort";
        Praktikumsbeauftragter pb = new Praktikumsbeauftragter();
        pb.setUsername(username);
        pb.setPasswort(correctPassword);

        LoginAnfragePB loginAnfrage = new LoginAnfragePB(username, wrongPassword);

        when(pbRepository.findByUsername(username)).thenReturn(Optional.of(pb));

        boolean result = loginService.login(loginAnfrage);

        assertFalse(result, "Login schlägt fehl wenn Passwort falsch ist");
    }

    @Test
    void loginPB_ThrowException_WennUserNichtExistiert() {
        String username = "Nicht vorhandener UserName";
        String password = "irgenteinPasswort";
        LoginAnfragePB loginAnfrage = new LoginAnfragePB(username, password);

        when(pbRepository.findByUsername(username)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> loginService.login(loginAnfrage));
        assertEquals("Username ist falsch", exception.getMessage());
    }
}

