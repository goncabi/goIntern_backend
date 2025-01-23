package com.example.application.services;

import com.example.application.models.AppUserRole;
import com.example.application.models.Praktikumsantrag;
import com.example.application.models.Praktikumsbeauftragter;
import com.example.application.models.StatusAntrag;
import com.example.application.models.Benachrichtigung;
import com.example.application.repositories.BenachrichtigungRepository;
import com.example.application.repositories.PBRepository;
import com.example.application.repositories.PraktikumsantragRepository;
import org.junit.jupiter.api.Test;
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
        antrag.setStatusAntrag(StatusAntrag.EINGEREICHT); // Status setzen
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
        // Vorbereitung: Mock eines gültigen Praktikumsantrags
        String matrikelnummer = "s1234567";
        Praktikumsantrag antrag = new Praktikumsantrag();
        antrag.setMatrikelnummer(matrikelnummer);
        antrag.setStatusAntrag(StatusAntrag.EINGEREICHT);

        // Mock für Repository: Antrag wird gefunden
        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer))
                .thenReturn(Optional.of(antrag));

        // Mock für Speichern: Speichert denselben Antrag und gibt ihn zurück
        when(praktikumsantragRepository.save(any(Praktikumsantrag.class)))
                .thenAnswer(invocation -> invocation.getArgument(0)); // Gibt das gespeicherte Objekt zurück

        // Test der Methode
        Praktikumsantrag result = pbService.antragGenehmigen(matrikelnummer);

        // Überprüfen der Ergebnisse
        assertNotNull(result, "Result darf nicht null sein"); // Sicherstellen, dass ein Antrag zurückgegeben wird
        assertEquals(StatusAntrag.ZUGELASSEN, result.getStatusAntrag(), "Status sollte auf ZUGELASSEN gesetzt werden");

        // Verifizieren, dass der Antrag gespeichert wurde
        verify(praktikumsantragRepository, times(1)).save(antrag);
        verify(praktikumsantragRepository, times(1)).findByMatrikelnummer(matrikelnummer);
    }
    @Test
    void testAntragGenehmigenMitNullMatrikelnummer() {
        // Vorbereitung: Matrikelnummer ist null
        String matrikelnummer = null;

        // Test der Methode und Erwartung einer IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> pbService.antragGenehmigen(matrikelnummer));

        // Überprüfen der Fehlermeldung
        assertEquals("Antrag wurde nicht gefunden", exception.getMessage());

        // Verifizieren, dass keine Interaktion mit dem Repository erfolgt ist
        verify(praktikumsantragRepository, never()).save(any(Praktikumsantrag.class));
        verify(praktikumsantragRepository, never()).findByMatrikelnummer(anyString());
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
    void testAntragAblehnenErfolgreich() {
        // Vorbereitung: Mock eines Praktikumsantrags
        String matrikelnummer = "s1234567";
        String kommentarJson = "{\"kommentar\":\"Die Unterlagen sind unvollständig.\"}";

        Praktikumsantrag antrag = new Praktikumsantrag();
        antrag.setMatrikelnummer(matrikelnummer);
        antrag.setNameStudentin("Müller");
        antrag.setStatusAntrag(StatusAntrag.EINGEREICHT);

        // Mock für Repository
        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer))
                .thenReturn(Optional.of(antrag));

        // Test der Methode
        String result = pbService.antragAblehnen(matrikelnummer, kommentarJson);

        // Überprüfen der Ergebnisse
        assertEquals("Der Antrag von Müller wurde erfolgreich abgelehnt und die Nachricht übermittelt.", result);
        assertEquals(StatusAntrag.ABGELEHNT, antrag.getStatusAntrag());

        // Verifizieren, dass die Benachrichtigung gespeichert wurde
        verify(benachrichtigungRepository, times(1)).save(any(Benachrichtigung.class));
    }


    @Test
    void testAntragAblehnen_AntragNichtGefunden() {
        when(praktikumsantragRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> pbService.antragAblehnen("1234567", "Falsche Daten"));

        assertEquals("Fehler beim Finden des Praktikumsantrags.", exception.getMessage());
    }





}