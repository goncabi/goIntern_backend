package com.example.application.services;

import com.example.application.models.Praktikumsantrag;
import com.example.application.models.StatusAntrag;
import com.example.application.repositories.BenachrichtigungRepository;
import com.example.application.repositories.PraktikumsantragRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional // alle Daten Änderungen im Datenbank in Test-Kontext werden automatisch rückgängig gemacht
@Validated //Aktiviert die Validierung, sodass @Valid funktioniert

class PraktikumsantragServiceTest {

    @Autowired
    private PraktikumsantragService praktikumsantragService;

    @MockBean
    private PraktikumsantragRepository praktikumsantragRepository;

    @MockBean
    private BenachrichtigungRepository benachrichtigungRepository;

    @MockBean
    private PBService pbService;

    private Praktikumsantrag erzeugeGueltigenAntrag() {
        Praktikumsantrag antrag = new Praktikumsantrag();
        antrag.setAntragsID(111L);
        antrag.setMatrikelnummer("1212120");
        antrag.setNameStudentin("Hunt");
        antrag.setVornameStudentin("Maria");
        antrag.setGebDatumStudentin(LocalDate.of(1999, 3, 11));
        antrag.setStrasseHausnummerStudentin("Friedrichstr. 15");
        antrag.setPlzStudentin(10598);
        antrag.setOrtStudentin("Berlin");
        antrag.setTelefonnummerStudentin("01478112530");
        antrag.setEmailStudentin("mariahunt@gmail.com");
        antrag.setPraktikumssemester("WiSe2025");
        antrag.setStudiensemester(3);
        antrag.setStudiengang("Informatik");
        antrag.setDatumAntrag(LocalDate.of(2024, 11, 15));
        antrag.setNamePraktikumsstelle("Tech Solutions");
        antrag.setStrassePraktikumsstelle("Hauptstrasse");
        antrag.setPlzPraktikumsstelle(10117);
        antrag.setOrtPraktikumsstelle("Berlin");
        antrag.setBundeslandPraktikumsstelle("Berlin");
        antrag.setLandPraktikumsstelle("Deutschland");
        antrag.setAnsprechpartnerPraktikumsstelle("Herr Schmidt");
        antrag.setTelefonPraktikumsstelle("0123456789");
        antrag.setEmailPraktikumsstelle("contact@techsolutions.com");
        antrag.setAbteilung("IT");
        antrag.setTaetigkeit("Qualitätssicherung");
        antrag.setStartdatum(LocalDate.of(2024, 11, 15));
        antrag.setEnddatum(LocalDate.of(2024, 11, 16));
        antrag.setStatusAntrag(StatusAntrag.GESPEICHERT);
        antrag.setAuslandspraktikum(true);
        return antrag;
    }

    @Test
    void testUpdateStatusErfolgreich() {
        String matrikelnummer = "123456";
        Praktikumsantrag antrag = new Praktikumsantrag();
        antrag.setMatrikelnummer(matrikelnummer);
        antrag.setStatusAntrag(StatusAntrag.GESPEICHERT);

        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(antrag));

        praktikumsantragService.updateStatus(matrikelnummer, StatusAntrag.ZUGELASSEN);

        assertEquals(StatusAntrag.ZUGELASSEN, antrag.getStatusAntrag());
        verify(praktikumsantragRepository, times(1)).findByMatrikelnummer(matrikelnummer);
        verify(praktikumsantragRepository, times(1)).save(antrag);
    }

    @Test
    void testUpdateStatus_AntragNichtGefunden() {
        String matrikelnummer = "123456";
        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                praktikumsantragService.updateStatus(matrikelnummer, StatusAntrag.ZUGELASSEN));
        assertEquals("Fehler beim Aufrufen des Antrags.", exception.getMessage());

        verify(praktikumsantragRepository, times(1)).findByMatrikelnummer(matrikelnummer);
    }

    @Test
    void testUpdateStatus_StatusWirdAktualisiert() {
        String matrikelnummer = "123456";
        Praktikumsantrag antrag = new Praktikumsantrag();
        antrag.setMatrikelnummer(matrikelnummer);
        antrag.setStatusAntrag(StatusAntrag.GESPEICHERT);

        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(antrag));

        praktikumsantragService.updateStatus(matrikelnummer, StatusAntrag.ZUGELASSEN);

        assertEquals(StatusAntrag.ZUGELASSEN, antrag.getStatusAntrag());
        verify(praktikumsantragRepository, times(1)).save(antrag);
    }

    @Test
    void testUpdateStatus_AbgelehnterAntrag() {
        String matrikelnummer = "123456";
        Praktikumsantrag antrag = new Praktikumsantrag();
        antrag.setMatrikelnummer(matrikelnummer);
        antrag.setStatusAntrag(StatusAntrag.ABGELEHNT);

        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(antrag));

        praktikumsantragService.updateStatus(matrikelnummer, StatusAntrag.GESPEICHERT);

        assertEquals(StatusAntrag.GESPEICHERT, antrag.getStatusAntrag());
        verify(praktikumsantragRepository, times(1)).save(antrag);
    }

    @Test
    void testUpdateStatus_UnveraenderterStatus() {
        String matrikelnummer = "123456";
        Praktikumsantrag antrag = new Praktikumsantrag();
        antrag.setMatrikelnummer(matrikelnummer);
        antrag.setStatusAntrag(StatusAntrag.ZUGELASSEN);

        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(antrag));

        praktikumsantragService.updateStatus(matrikelnummer, StatusAntrag.ZUGELASSEN);

        assertEquals(StatusAntrag.ZUGELASSEN, antrag.getStatusAntrag());
        verify(praktikumsantragRepository, times(1)).save(antrag);
    }

    @Test
    void testAntragSpeichernErfolgreichmitAbgelehnt() {
        String matrikelnummer = "12345678";
        Praktikumsantrag praktikumsantrag = erzeugeGueltigenAntrag();
        praktikumsantrag.setMatrikelnummer(matrikelnummer);
        praktikumsantrag.setStatusAntrag(StatusAntrag.ABGELEHNT);

        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(praktikumsantrag));

        praktikumsantragService.antragSpeichern(praktikumsantrag);

        assertEquals(2, praktikumsantrag.getAntragsVersion());

        verify(praktikumsantragRepository, times(1)).save(any(Praktikumsantrag.class));
    }

    @Test
    void testAntragSpeichernErfolgreich() {
        String matrikelnummer = "12345678";
        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.empty());

        praktikumsantragService.antragSpeichern(erzeugeGueltigenAntrag());

        verify(praktikumsantragRepository, times(1)).save(any(Praktikumsantrag.class));
    }

    @Test
    void testAntragErstellenMitVorhandenemAntrag() {
        // Crear un objeto Praktikumsantrag con datos válidos
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();

        // Simular que el Praktikumsantrag ya existe en el repositorio
        when(praktikumsantragRepository.findByMatrikelnummer(antrag.getMatrikelnummer()))
                .thenReturn(Optional.of(antrag));

        praktikumsantragService.antragSpeichern(antrag);

        verify(praktikumsantragRepository, times(1)).findByMatrikelnummer(antrag.getMatrikelnummer());

        verify(praktikumsantragRepository, times(1)).save(antrag);
    }

    @Test
    void testAntragSpeichernMitNullName() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setNameStudentin(null);

        when(praktikumsantragRepository.findByMatrikelnummer(antrag.getMatrikelnummer()))
                .thenReturn(Optional.empty());

        praktikumsantragService.antragSpeichern(antrag);

        verify(praktikumsantragRepository, times(1)).save(antrag);
    }

    @Test
    void testAntragSpeichernMitNullEmail() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setEmailStudentin(null);

        when(praktikumsantragRepository.findByMatrikelnummer(antrag.getMatrikelnummer()))
                .thenReturn(Optional.empty());

        praktikumsantragService.antragSpeichern(antrag);

        verify(praktikumsantragRepository, times(1)).save(antrag);
    }

    @Test
    void testAntragSpeichernMitMehrerenNullen() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setNameStudentin(null);
        antrag.setEmailStudentin(null);
        antrag.setTelefonnummerStudentin(null);
        antrag.setAbteilung(null);

        when(praktikumsantragRepository.findByMatrikelnummer(antrag.getMatrikelnummer()))
                .thenReturn(Optional.empty());

        praktikumsantragService.antragSpeichern(antrag);

        verify(praktikumsantragRepository, times(1)).save(antrag);
    }

    @Test
    void testAntragSpeichernAntragMitNullWerten() {
        // Vorhandener Antrag im Repository
        Praktikumsantrag vorhandenerAntrag = new Praktikumsantrag();
        vorhandenerAntrag.setMatrikelnummer("s1234567");
        vorhandenerAntrag.setNameStudentin("Hunt");
        vorhandenerAntrag.setEmailStudentin("mariahunt@gmail.com");

        when(praktikumsantragRepository.findByMatrikelnummer("s1234567"))
                .thenReturn(Optional.of(vorhandenerAntrag));

        // Neuer Antrag mit null-Werten
        Praktikumsantrag neuerAntrag = new Praktikumsantrag();
        neuerAntrag.setMatrikelnummer("s1234567");
        neuerAntrag.setNameStudentin(null); // Name bleibt gleich
        neuerAntrag.setEmailStudentin(null); // Email bleibt gleich
        neuerAntrag.setOrtStudentin("Berlin"); // Neues Feld wird aktualisiert

        // Aufruf der Methode
        praktikumsantragService.antragSpeichern(neuerAntrag);

        // Überprüfen, dass die ursprünglichen Werte nicht überschrieben wurden
        assertEquals("Hunt", vorhandenerAntrag.getNameStudentin()); // Der ursprüngliche Wert bleibt
        assertEquals("mariahunt@gmail.com", vorhandenerAntrag.getEmailStudentin()); // Der ursprüngliche Wert bleibt

        // Überprüfen, dass das neue Feld aktualisiert wurde
        assertEquals("Berlin", vorhandenerAntrag.getOrtStudentin());

        // Verifizieren, dass das Repository das aktualisierte Objekt speichert
        verify(praktikumsantragRepository, times(1)).save(vorhandenerAntrag);
    }

    @Test
    void testAntragSpeichernMitLeererMatrikelnummer() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setMatrikelnummer("  "); // Leere Matrikelnummer

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> praktikumsantragService.antragSpeichern(antrag));

        assertEquals("Die Matrikelnummer darf nicht leer sein.", exception.getMessage());

        verify(praktikumsantragRepository, times(0)).findByMatrikelnummer(anyString());
        verify(praktikumsantragRepository, times(0)).save(any());
    }

    @Test
    void testAntragUebermittelnMitFehlerhaftenDaten() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setStartdatum(LocalDate.of(2024, 12, 15));
        antrag.setEnddatum(LocalDate.of(2024, 11, 15));

        assertThrows(IllegalArgumentException.class, () -> praktikumsantragService.antragUebermitteln(antrag));
    }

    @Test
    void testAntragUebermittelnMitGueltigenAngaben() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        when(praktikumsantragRepository.findByMatrikelnummer(antrag.getMatrikelnummer()))
                .thenReturn(Optional.of(antrag)); // Simula que el Antrag ya está presente
        doNothing().when(pbService).antragUebermitteln(any(Praktikumsantrag.class)); // Simula el PBService

        praktikumsantragService.antragUebermitteln(antrag);

        verify(praktikumsantragRepository, times(1)).findByMatrikelnummer(antrag.getMatrikelnummer());
        verify(pbService, times(1)).antragUebermitteln(antrag);
    }

    @Test
    void testAntragUebermittelnAntragNichtVorhanden() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();

        when(praktikumsantragRepository.findByMatrikelnummer(antrag.getMatrikelnummer()))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() -> praktikumsantragService.antragUebermitteln(antrag));

        verify(praktikumsantragRepository, times(1)).findByMatrikelnummer(antrag.getMatrikelnummer());
        verify(pbService, times(1)).antragUebermitteln(antrag);
    }

    @Test
    void testAntragUebermittelnStartdatumNachEnddatum() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setStartdatum(LocalDate.of(2024, 12, 15));
        antrag.setEnddatum(LocalDate.of(2024, 11, 15)); // Startdatum nach Enddatum

        when(praktikumsantragRepository.findByMatrikelnummer(antrag.getMatrikelnummer()))
                .thenReturn(Optional.of(antrag));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> praktikumsantragService.antragUebermitteln(antrag));

        assertEquals("Das Startdatum darf nicht nach dem Enddatum liegen.", exception.getMessage());
    }

    // hier in der AntragLoeschen Methode wird auch noch getestet ob die antragZurueckgezogen Methode aufgerufen wird:
    @Test
    void testAntragLoeschenErfolgreich() {
        String matrikelnummer = "1012120";
        Praktikumsantrag antrag = erzeugeGueltigenAntrag(); // hier antrag erzeugen
        antrag.setMatrikelnummer(matrikelnummer); //der antrag bekommt die matrikelnummer "1012120"

        // MockTeil:
        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(antrag));
        doNothing().when(praktikumsantragRepository).delete(antrag);
        doNothing().when(pbService).antragZurueckgezogen(matrikelnummer);

        //Test Teil:
        praktikumsantragService.antragLoeschen(matrikelnummer);

        verify(praktikumsantragRepository, times(1)).findByMatrikelnummer(matrikelnummer);
        verify(praktikumsantragRepository, times(1)).deleteById(antrag.getAntragsID());
    }

    @Test
    void testAntragLoeschenNichtVorhanden() {
        String matrikelnummer = "s123"; // Se asegura que id está inicializado

        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> praktikumsantragService.antragLoeschen(matrikelnummer));

        assertEquals("Praktikumsantrag für die Matrikelnummer: " + matrikelnummer + " ist nicht vorhanden und kann nicht gelöscht werden",
                exception.getMessage());

        verify(praktikumsantragRepository, times(1)).findByMatrikelnummer(matrikelnummer);
        verify(praktikumsantragRepository, times(0)).deleteById(any());
    }

    @Test
    void testAntragLoeschenMitNullId() {
        String matrikelnummer = null;

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> praktikumsantragService.antragLoeschen(matrikelnummer));

        assertEquals("Praktikumsantrag für die Matrikelnummer: null ist nicht vorhanden und kann nicht gelöscht werden",
                exception.getMessage());

        verify(praktikumsantragRepository, times(0)).findById(any());
        verify(praktikumsantragRepository, times(0)).deleteById(any());
    }

    @Test
    void testAntragLoeschenMitZugelassenemAntrag() {
        String matrikelnummer = "1012120";
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setMatrikelnummer(matrikelnummer);
        antrag.setStatusAntrag(StatusAntrag.ZUGELASSEN);

        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(antrag));
        doNothing().when(praktikumsantragRepository).delete(antrag);
        doNothing().when(pbService).antragZurueckgezogen(matrikelnummer);

        praktikumsantragService.antragLoeschen(matrikelnummer);

        verify(praktikumsantragRepository, times(1)).findByMatrikelnummer(matrikelnummer);
        verify(praktikumsantragRepository, times(1)).deleteById(antrag.getAntragsID());
        verify(pbService, times(1)).antragZurueckgezogen(matrikelnummer);
    }

    @Test
    void testAntragLoeschenMitAbgelehntemAntrag() {
        String matrikelnummer = "1012120";
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setMatrikelnummer(matrikelnummer);
        antrag.setStatusAntrag(StatusAntrag.ABGELEHNT);

        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(antrag));
        doNothing().when(praktikumsantragRepository).delete(antrag);

        praktikumsantragService.antragLoeschen(matrikelnummer);

        verify(praktikumsantragRepository, times(1)).findByMatrikelnummer(matrikelnummer);
        verify(praktikumsantragRepository, times(1)).deleteById(antrag.getAntragsID());
        verify(pbService, never()).antragZurueckgezogen(matrikelnummer);
    }

    @Test
    void testGetAllAntraege() {
        List<Praktikumsantrag> antraege = List.of(erzeugeGueltigenAntrag());
        when(praktikumsantragRepository.findAll()).thenReturn(antraege);

        List<Praktikumsantrag> result = praktikumsantragService.getAllAntraege();

        assertEquals(antraege, result);
        verify(praktikumsantragRepository, times(1)).findAll();
    }

    @Test
    void testGetAllAntraegeMitLeererDatenbank() {
        when(praktikumsantragRepository.findAll()).thenReturn(List.of());

        List<Praktikumsantrag> result = praktikumsantragService.getAllAntraege();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(praktikumsantragRepository, times(1)).findAll();
    }

    @Test
    void testGetAllAntraegeMitMehrerenAntraegen() {
        Praktikumsantrag antrag1 = erzeugeGueltigenAntrag();
        Praktikumsantrag antrag2 = erzeugeGueltigenAntrag();
        antrag2.setMatrikelnummer("87654321");

        List<Praktikumsantrag> antraege = List.of(antrag1, antrag2);

        when(praktikumsantragRepository.findAll()).thenReturn(antraege);

        List<Praktikumsantrag> result = praktikumsantragService.getAllAntraege();

        assertEquals(2, result.size());
        assertTrue(result.contains(antrag1));
        assertTrue(result.contains(antrag2));
        verify(praktikumsantragRepository, times(1)).findAll();
    }

   @Test
    void testUpdateAntragStatusToImPraktikum() {
        String matrikelnummer = "123456";
        Praktikumsantrag antrag = new Praktikumsantrag();
        antrag.setMatrikelnummer(matrikelnummer);
        antrag.setStatusAntrag(StatusAntrag.ZUGELASSEN);
        antrag.setStartdatum(LocalDate.of(2025, 1, 1));
        antrag.setEnddatum(LocalDate.of(2025, 6, 30));

        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(antrag));

        praktikumsantragService.updateAntragStatus(matrikelnummer);

        assertEquals(StatusAntrag.IMPRAKTIKUM, antrag.getStatusAntrag());
        verify(praktikumsantragRepository).save(antrag);
    }


    @Test
    void testUpdateAntragStatusToAbsolviert() {
        String matrikelnummer = "123456";
        Praktikumsantrag antrag = new Praktikumsantrag();
        antrag.setMatrikelnummer(matrikelnummer);
        antrag.setStatusAntrag(StatusAntrag.ZUGELASSEN);
        antrag.setStartdatum(LocalDate.of(2024, 7, 1));
        antrag.setEnddatum(LocalDate.of(2024, 12, 31));

        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.of(antrag));

        praktikumsantragService.updateAntragStatus(matrikelnummer);

        assertEquals(StatusAntrag.ABSOLVIERT, antrag.getStatusAntrag());
        verify(praktikumsantragRepository).save(antrag);
    }

    @Test
    void testUpdateAntragStatusNotFound() {
        String matrikelnummer = "123456";
        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> praktikumsantragService.updateAntragStatus(matrikelnummer));

        verify(praktikumsantragRepository, times(1)).findByMatrikelnummer(matrikelnummer);
        verify(praktikumsantragRepository, never()).save(any(Praktikumsantrag.class));
    }
}

