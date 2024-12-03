package com.example.application.services;

import com.example.application.models.benachrichtigung.Benachrichtigung;
import com.example.application.models.benachrichtigung.LeseStatus;
import com.example.application.repositories.BenachrichtigungRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BenachrichtigungServiceTest {

    @Autowired
    private BenachrichtigungService benachrichtigungService;

    @MockBean
    private BenachrichtigungRepository benachrichtigungRepository;

    @Test
    void testAlleLesen() {
        //Daten vorbereiten
        String empfaenger = "testUser";
        Benachrichtigung b1 = new Benachrichtigung("Test1", new Date(), LeseStatus.UNGELESEN, empfaenger);
        Benachrichtigung b2 = new Benachrichtigung("Test2", new Date(), LeseStatus.UNGELESEN, empfaenger);
        List<Benachrichtigung> mockResult = Arrays.asList(b1, b2);
        //Repository verhalten mocken
        when(benachrichtigungRepository.findByEmpfaengerOrderByDatum(empfaenger))
                .thenReturn(mockResult);
        //Methode aufrufen
        List<Benachrichtigung> result = benachrichtigungService.alleLesen(empfaenger);
        // Überprüfen, ob das Ergebnis wie erwartet ist
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test1", result.get(0).getNachricht());
        assertEquals("Test2", result.get(1).getNachricht());
        // Verifizieren, dass das Repository korrekt aufgerufen wurde
        verify(benachrichtigungRepository, times(1)).findByEmpfaengerOrderByDatum(empfaenger);
    }

    @Test
    void testAlleLesen_KeineNachrichten() {
        String empfaenger = "testUser";
        when(benachrichtigungRepository.findByEmpfaengerOrderByDatum(empfaenger))
                .thenReturn(Collections.emptyList());

        List<Benachrichtigung> result = benachrichtigungService.alleLesen(empfaenger);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(benachrichtigungRepository, times(1)).findByEmpfaengerOrderByDatum(empfaenger);
    }

    @Test
    void testUngeleseneLesen() {
        String empfaenger = "testUser";
        Benachrichtigung b1 = new Benachrichtigung("Test1", new Date(), LeseStatus.UNGELESEN, empfaenger);
        Benachrichtigung b2 = new Benachrichtigung("Test2", new Date(), LeseStatus.GELESEN, empfaenger);
        List<Benachrichtigung> mockResult = Arrays.asList(b1, b2);

        when(benachrichtigungRepository.findByEmpfaengerOrderByDatum(empfaenger))
                .thenReturn(mockResult);

        List<Benachrichtigung> result = benachrichtigungService.ungeleseneLesen(empfaenger);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test1", result.getFirst().getNachricht());

        verify(benachrichtigungRepository, times(1)).findByEmpfaengerOrderByDatum(empfaenger);
    }

    @Test
    void testUngeleseneLesen_KeineUngelesen() {
        String empfaenger = "testUser";
        Benachrichtigung b1 = new Benachrichtigung("Test1", new Date(), LeseStatus.GELESEN, empfaenger);
        Benachrichtigung b2 = new Benachrichtigung("Test2", new Date(), LeseStatus.GELESEN, empfaenger);
        List<Benachrichtigung> mockResult = Arrays.asList(b1, b2);

        when(benachrichtigungRepository.findByEmpfaengerOrderByDatum(empfaenger))
                .thenReturn(mockResult);

        List<Benachrichtigung> result = benachrichtigungService.ungeleseneLesen(empfaenger);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(benachrichtigungRepository, times(1)).findByEmpfaengerOrderByDatum(empfaenger);
    }

    @Test
    void testUngeleseneLesen_KeineNachrichten() {
        String empfaenger = "testUser";
        when(benachrichtigungRepository.findByEmpfaengerOrderByDatum(empfaenger))
                .thenReturn(Collections.emptyList());

        List<Benachrichtigung> result = benachrichtigungService.ungeleseneLesen(empfaenger);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(benachrichtigungRepository, times(1)).findByEmpfaengerOrderByDatum(empfaenger);
    }

    @Test
    void existierenUngelesene_true() {
        String empfaenger = "testUser";
        Benachrichtigung b1 = new Benachrichtigung("Test1", new Date(), LeseStatus.UNGELESEN, empfaenger);
        List<Benachrichtigung> mockResult = List.of(b1);

        when(benachrichtigungRepository.findByEmpfaengerOrderByDatum(empfaenger))
                .thenReturn(mockResult);

        boolean result = benachrichtigungService.existierenUngelesene(empfaenger);

        assertTrue(result);

        verify(benachrichtigungRepository, times(1)).findByEmpfaengerOrderByDatum(empfaenger);

    }

    @Test
    void existierenUngelesene_false() {
        String empfaenger = "testUser";
        Benachrichtigung b1 = new Benachrichtigung("Test1", new Date(), LeseStatus.GELESEN, empfaenger);
        List<Benachrichtigung> mockResult = List.of(b1);

        when(benachrichtigungRepository.findByEmpfaengerOrderByDatum(empfaenger))
                .thenReturn(mockResult);

        boolean result = benachrichtigungService.existierenUngelesene(empfaenger);

        assertFalse(result);

        verify(benachrichtigungRepository, times(1)).findByEmpfaengerOrderByDatum(empfaenger);
    }

    @Test
    void existierenUngelesene_KeineNachrichten() {
        String empfaenger = "testUser";

        when(benachrichtigungRepository.findByEmpfaengerOrderByDatum(empfaenger))
                .thenReturn(Collections.emptyList());

        boolean result = benachrichtigungService.existierenUngelesene(empfaenger);

        assertFalse(result);

        verify(benachrichtigungRepository, times(1)).findByEmpfaengerOrderByDatum(empfaenger);
    }
}