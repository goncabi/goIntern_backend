package com.example.application.services;

import com.example.application.models.AppUserRole;
import com.example.application.models.Praktikumsantrag;
import com.example.application.models.Praktikumsbeauftragter;
import com.example.application.models.StatusAntrag;
import com.example.application.models.benachrichtigung.Benachrichtigung;
import com.example.application.repositories.BenachrichtigungRepository;
import com.example.application.repositories.PBRepository;
import com.example.application.repositories.PraktikumsantragRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PBServiceTest {

    @Autowired
    private PBService pbService;

    @MockBean
    private PBRepository pBRepository; // Mock für das Repository

    @MockBean
    private BenachrichtigungRepository benachrichtigungRepository;
    @MockBean
    private PraktikumsantragRepository praktikumsantragRepository;

    private Praktikumsantrag erzeugeGueltigenAntrag() {
        Praktikumsantrag antrag = new Praktikumsantrag();
        antrag.setMatrikelnummer("12345678");
        return antrag;
    }

    private Praktikumsbeauftragter erzeugePraktikumsbeauftragter() {
        Praktikumsbeauftragter pb = new Praktikumsbeauftragter();
        pb.setUsername("admin_user");
        pb.setUserRole(AppUserRole.PRAKTIKUMSBEAUFTRAGTER);
        return pb;
    }


    @Test
    void testRun() throws Exception {
        Praktikumsbeauftragter mockPB = new Praktikumsbeauftragter("Jörn Freiheit", "AbInDieFreiheit13579!", AppUserRole.PRAKTIKUMSBEAUFTRAGTER);

        pbService.run();

        verify(pBRepository, atLeastOnce()).save(mockPB);
    }

    @Test
    void testRunThrowsException() {
        doThrow(new RuntimeException("Fehler beim Speichern")).when(pBRepository)
                                                              .save(any(Praktikumsbeauftragter.class));

        assertThrows(RuntimeException.class, () -> pbService.run());
    }

    @Test
    void testAntragGenehmigen() {
        // Vorbereitung
        Praktikumsantrag antrag = new Praktikumsantrag();
        antrag.setStatusAntrag(StatusAntrag.INBEARBEITUNG);

        pbService.antragGenehmigen(antrag);

        // hier testen
        assertEquals(StatusAntrag.ZUGELASSEN, antrag.getStatusAntrag());
    }

    @Test
    void testAntragGenehmigenWithNullAntrag() {
        Praktikumsantrag antrag = null;
        assertThrows(NullPointerException.class, () -> pbService.antragGenehmigen(antrag)); //wenn der Antrag null ist wird eine Exception geworfen
    }

    @Test
    void testAntragUebermittelnErfolgreich() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        Praktikumsbeauftragter pb = erzeugePraktikumsbeauftragter();

        when(pBRepository.findByUserRole(AppUserRole.PRAKTIKUMSBEAUFTRAGTER)).thenReturn(Optional.of(pb));

        pbService.antragUebermitteln(antrag);

        verify(pBRepository, times(1)).findByUserRole(AppUserRole.PRAKTIKUMSBEAUFTRAGTER);
        verify(benachrichtigungRepository, times(1)).save(any(Benachrichtigung.class));
    }

    @Test
    void testAntragUebermittelnKeinAdmin() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();

        when(pBRepository.findByUserRole(AppUserRole.PRAKTIKUMSBEAUFTRAGTER))
                .thenReturn(Optional.empty());


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> pbService.antragUebermitteln(antrag));

        assertEquals("Kein Praktikumsbeauftragter mit der Rolle ADMIN gefunden.", exception.getMessage());


        verify(benachrichtigungRepository, never()).save(any());
    }
    @Test
    void testAntragUebermittelnSpeichernFehler() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        Praktikumsbeauftragter pb = erzeugePraktikumsbeauftragter();

        when(pBRepository.findByUserRole(AppUserRole.PRAKTIKUMSBEAUFTRAGTER))
                .thenReturn(Optional.of(pb));

        doThrow(new RuntimeException("Fehler beim Speichern der Benachrichtigung"))
                .when(benachrichtigungRepository).save(any(Benachrichtigung.class));

        RuntimeException exception = assertThrows(RuntimeException.class, //Tun, als ob eine Exception aufgerufen wird.
                () -> pbService.antragUebermitteln(antrag));

        assertEquals("Fehler beim Speichern der Benachrichtigung", exception.getMessage()); //sodass wir Fehler beim Speichern der Nachrichten ausschliessen können


        verify(pBRepository, times(1)).findByUserRole(AppUserRole.PRAKTIKUMSBEAUFTRAGTER);
        verify(benachrichtigungRepository, times(1)).save(any(Benachrichtigung.class));
    }

    @Test
    void testAntragAblehnen(){
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setAntragsID(1L);
        antrag.setNameStudentin("Frau Held ");
        when(praktikumsantragRepository.findById(1L)).thenReturn(Optional.of(antrag));

        String result = pbService.antragAblehnen(1, "Falsche Daten");

        assertEquals(StatusAntrag.ABGELEHNT, antrag.getStatusAntrag());
        assertEquals("Der Antrag von Frau Held wurde erfolgreich abgelehnt und die Nachricht übermittelt.", result);
        verify(praktikumsantragRepository, times(2)).findById(1L);
        verify(benachrichtigungRepository, times(1)).save(Mockito.any());
    }

    @Test
    void testAntragAblehnen_AntragNichtGefunden() {
        when(praktikumsantragRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> pbService.antragAblehnen(1, "Falsche Daten"));

        assertEquals("Fehler beim Finden des Praktikumsantrags.", exception.getMessage());
    }
}