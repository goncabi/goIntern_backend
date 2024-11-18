package com.example.application.services;
import com.example.application.models.LoginAnfrage;
import com.example.application.models.Studentin;
import com.example.application.repositories.StudentinRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class LoginServiceTest {

    @Mock
    private StudentinRepository studentinRepository;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_shouldReturnTrue_whenCredentialsAreCorrect() {
        // Arrange
        String matrikelnummer = "s0123456";
        String password = "password1!";
        Studentin studentin = new Studentin();
        studentin.setMatrikelnummer(matrikelnummer);
        studentin.setPassword(password);

        LoginAnfrage loginAnfrage = new LoginAnfrage(matrikelnummer, password);

        when(studentinRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(studentin));

        // Act
        boolean result = loginService.login(loginAnfrage);

        // Assert
        assertTrue(result, "Login successfull");
    }

    @Test
    void login_shouldReturnFalse_whenPasswordIsIncorrect() {

        String matrikelnummer = "s0123456";
        String correctPassword = "correctPassword";
        String wrongPassword = "wrongPassword";
        Studentin studentin = new Studentin();
        studentin.setMatrikelnummer(matrikelnummer);
        studentin.setPassword(correctPassword);

        LoginAnfrage loginAnfrage = new LoginAnfrage(matrikelnummer, wrongPassword);

        when(studentinRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(studentin));

        // Act
        boolean result = loginService.login(loginAnfrage);

        // Assert
        assertFalse(result, "Login fails, because password is incorrect");
    }

    @Test
    void login_shouldThrowException_whenMatrikelnummerDoesNotExist() {

        String matrikelnummer = "s0123456";
        String password = "password";
        LoginAnfrage loginAnfrage = new LoginAnfrage(matrikelnummer, password);

        when(studentinRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> loginService.login(loginAnfrage));
        assertEquals("Matrikelnummer falsch oder nicht registriert", exception.getMessage());
    }
}