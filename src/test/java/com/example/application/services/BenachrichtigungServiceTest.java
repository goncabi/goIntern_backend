package com.example.application.services;

import com.example.application.models.AppUserRole;
import com.example.application.models.Benachrichtigung;
import com.example.application.models.BenachrichtigungWichtigkeit;
import com.example.application.models.Praktikumsbeauftragter;
import com.example.application.repositories.BenachrichtigungRepository;
import com.example.application.repositories.PBRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BenachrichtigungServiceTest {

    @Autowired
    private BenachrichtigungService benachrichtigungService;

    @MockBean
    private BenachrichtigungRepository benachrichtigungRepository;

    @MockBean
    private PBRepository pbRepository;

    @Test
    void testAlleLesen_success() {
        //Daten vorbereiten
        String empfaenger = "testUser";
        Benachrichtigung b1 = new Benachrichtigung("Test1", new Date(), empfaenger);
        Benachrichtigung b2 = new Benachrichtigung("Test2", new Date(), empfaenger);
        List<Benachrichtigung> mockResult = Arrays.asList(b1, b2);
        //Repository verhalten mocken
        when(benachrichtigungRepository.findByEmpfaengerOrderByDatumDesc(empfaenger))
                .thenReturn(mockResult);
        //Methode aufrufen
        List<Benachrichtigung> result = benachrichtigungService.alleLesen(empfaenger);
        // Überprüfen, ob das Ergebnis wie erwartet ist
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test1", result.get(0).getNachricht());
        assertEquals("Test2", result.get(1).getNachricht());
        // Verifizieren, dass das Repository korrekt aufgerufen wurde
        verify(benachrichtigungRepository, times(1)).findByEmpfaengerOrderByDatumDesc(empfaenger);
    }

    @Test
    void testAlleLesen_KeineNachrichten() {
        String empfaenger = "testUser";
        when(benachrichtigungRepository.findByEmpfaengerOrderByDatumDesc(empfaenger))
                .thenReturn(Collections.emptyList());

        List<Benachrichtigung> result = benachrichtigungService.alleLesen(empfaenger);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(benachrichtigungRepository, times(1)).findByEmpfaengerOrderByDatumDesc(empfaenger);
    }

    @Test
    void testUnwichtigeLoeschen_success(){
        String empfaenger = "testUser";
        Benachrichtigung b1 = new Benachrichtigung("Test1", new Date(), empfaenger);
        Benachrichtigung b2 = new Benachrichtigung("Test2", new Date(), empfaenger);
        List<Benachrichtigung> mockResult = Arrays.asList(b1, b2);

        when(benachrichtigungRepository.findByWichtigkeitAndEmpfaenger(BenachrichtigungWichtigkeit.UNWICHTIG, empfaenger))
                .thenReturn(mockResult);

        benachrichtigungService.unwichtigeNachrichtenLoeschen(empfaenger);

        verify(benachrichtigungRepository, times(1)).deleteAll(mockResult);
    }

    @Test
    void testUnwichtigeLoeschen_KeineNachrichten() {
        String empfaenger = "testUser";
        when(benachrichtigungRepository.findByWichtigkeitAndEmpfaenger(BenachrichtigungWichtigkeit.UNWICHTIG, empfaenger))
                .thenReturn(Collections.emptyList());
        benachrichtigungService.unwichtigeNachrichtenLoeschen(empfaenger);
        verify(benachrichtigungRepository, times(1)).deleteAll(Collections.emptyList());
    }

    @Test
    void testArbeitstageNachrichtLoeschen_success(){
        String empfaenger = "testUser";
        Benachrichtigung b1 = new Benachrichtigung("Test1", new Date(), empfaenger);
        Benachrichtigung b2 = new Benachrichtigung(empfaenger + ": Test2", new Date(), "Jörn Freiheit");
        b1.setWichtigkeit(BenachrichtigungWichtigkeit.WICHTIG);
        b2.setWichtigkeit(BenachrichtigungWichtigkeit.WICHTIG);
        List<Benachrichtigung> mockResult = Arrays.asList(b1, b2);

        when(benachrichtigungRepository.findByWichtigkeit(BenachrichtigungWichtigkeit.WICHTIG))
                .thenReturn(mockResult);

        benachrichtigungService.arbeitstagenachrichtenLoeschen(empfaenger);
        verify(benachrichtigungRepository, times(1)).delete(b1);
        verify(benachrichtigungRepository, times(1)).delete(b2);
    }

    @Test
    void testArbeitstageNachrichtLoeschen_NurWichtigeWerdenGelöscht() {
        String empfaenger = "testUser";
        Benachrichtigung b1 = new Benachrichtigung("Test1", new Date(), empfaenger);
        Benachrichtigung b2 = new Benachrichtigung(empfaenger + ": Test2", new Date(), "Jörn Freiheit");
        b1.setWichtigkeit(BenachrichtigungWichtigkeit.WICHTIG);
        List<Benachrichtigung> mockResult = Arrays.asList(b1, b2);

        when(benachrichtigungRepository.findByWichtigkeit(BenachrichtigungWichtigkeit.WICHTIG))
                .thenReturn(mockResult);

        benachrichtigungService.arbeitstagenachrichtenLoeschen(empfaenger);
        verify(benachrichtigungRepository, times(1)).delete(b1);
    }

    @Test
    void testArbeitstageNachrichtLoeschen_NurNachrichtenMitEmpfängerWerdenGelöscht() {
        String empfaenger = "testUser";
        Benachrichtigung b1 = new Benachrichtigung("Test1", new Date(), empfaenger);
        Benachrichtigung b2 = new Benachrichtigung("Test2", new Date(), "Jörn Freiheit");
        b1.setWichtigkeit(BenachrichtigungWichtigkeit.WICHTIG);
        b2.setWichtigkeit(BenachrichtigungWichtigkeit.WICHTIG);
        List<Benachrichtigung> mockResult = Arrays.asList(b1, b2);

        when(benachrichtigungRepository.findByWichtigkeit(BenachrichtigungWichtigkeit.WICHTIG))
                .thenReturn(mockResult);

        benachrichtigungService.arbeitstagenachrichtenLoeschen(empfaenger);
        verify(benachrichtigungRepository, times(1)).delete(b1);
    }

    @Test
    void testArbeitstageNachrichtLoeschen_KeineWichtigenNachrichten() {
        String empfaenger = "testUser";
        when(benachrichtigungRepository.findByWichtigkeit(BenachrichtigungWichtigkeit.WICHTIG))
                .thenReturn(Collections.emptyList());

        benachrichtigungService.arbeitstagenachrichtenLoeschen(empfaenger);
        verify(benachrichtigungRepository, times(1)).findByWichtigkeit(BenachrichtigungWichtigkeit.WICHTIG);
    }

    @Test
    void testArbeitstageNachrichtSpeichern() {
        Benachrichtigung benachrichtigung1 = new Benachrichtigung("Test", new Date(), "empfängerin");
        Praktikumsbeauftragter pb = new Praktikumsbeauftragter("Jörn Freiheit", "1234", AppUserRole.PRAKTIKUMSBEAUFTRAGTER);
        Benachrichtigung benachrichtigung2 = new Benachrichtigung("empfängerin: Test", new Date(), "Jörn Freiheit");
        benachrichtigung2.setWichtigkeit(BenachrichtigungWichtigkeit.WICHTIG);

        when(pbRepository.findByUserRole(AppUserRole.PRAKTIKUMSBEAUFTRAGTER)).thenReturn(Optional.of(pb));

        benachrichtigungService.arbeitstageNachrichtenSpeichern(benachrichtigung1);

        assertEquals(BenachrichtigungWichtigkeit.WICHTIG, benachrichtigung1.getWichtigkeit());
        verify(benachrichtigungRepository, times(1)).save(benachrichtigung1);
        verify(benachrichtigungRepository, times(1)).save(benachrichtigung2);
    }

    @Test
    void testArbeitstageNachrichtenSpeichern_NullBenachrichtigung() {
        assertThrows(NullPointerException.class, () -> benachrichtigungService.arbeitstageNachrichtenSpeichern(null));
    }

    @Test
    void testArbeitstageNachrichtenSpeichern_KeinPraktikumsbeauftragter() {
        Benachrichtigung benachrichtigung = new Benachrichtigung("Nachricht", new Date(), "student@example.com");

        when(pbRepository.findByUserRole(AppUserRole.PRAKTIKUMSBEAUFTRAGTER)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> benachrichtigungService.arbeitstageNachrichtenSpeichern(benachrichtigung));
    }
}