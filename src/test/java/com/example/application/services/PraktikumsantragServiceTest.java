package com.example.application.services;

import com.example.application.models.Praktikumsantrag;
import com.example.application.models.StatusAntrag;
import com.example.application.repositories.PraktikumsantragRepository;
import jakarta.validation.ConstraintViolationException;
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
    private PBService pbService;


    private Praktikumsantrag erzeugeGueltigenAntrag() {
        Praktikumsantrag antrag = new Praktikumsantrag();
        antrag.setAntragsID(111L);
        antrag.setMatrikelnummer("1212120");
        antrag.setNameStudentin("Hunt");
        antrag.setVornameStudentin("Maria");
        antrag.setGebDatumStudentin(LocalDate.of(1999, 3, 11));
        antrag.setStrasseStudentin("Friedrichstr.");
        antrag.setHausnummerStudentin(34);
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
        antrag.setLandPraktikumsstelle("Deutschland");
        antrag.setAnsprechpartnerPraktikumsstelle("Herr Schmidt");
        antrag.setTelefonPraktikumsstelle("0123456789");
        antrag.setEmailPraktikumsstelle("contact@techsolutions.com");
        antrag.setAbteilung("IT");
        antrag.setTaetigkeit("Qualitätssicherung");
        antrag.setStartdatum(LocalDate.of(2024, 11, 15));
        antrag.setEnddatum(LocalDate.of(2024, 11, 16));
        antrag.setStatusAntrag(StatusAntrag.GESPEICHERT);
        return antrag;
    }

    @Test
    void testAntragVorhandenErfolgreich() {
        String matrikelnummer = "12345678";
        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer))
                .thenReturn(Optional.of(new Praktikumsantrag()));

        boolean result = praktikumsantragService.antragVorhanden(matrikelnummer);

        assertTrue(result);
        verify(praktikumsantragRepository, times(1)).findByMatrikelnummer(matrikelnummer);
    }

    @Test
    void testAntragVorhandenNichtErfolgreich() {
        String matrikelnummer = "12345678";
        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer))
                .thenReturn(Optional.empty());

        boolean result = praktikumsantragService.antragVorhanden(matrikelnummer);

        assertFalse(result);
        verify(praktikumsantragRepository, times(1)).findByMatrikelnummer(matrikelnummer);
    }

    @Test
    void testAntragErstellenErfolgreich() {
        String matrikelnummer = "12345678";
        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.empty());

        praktikumsantragService.antragErstellen(matrikelnummer);

        verify(praktikumsantragRepository, times(1)).save(any(Praktikumsantrag.class));
    }

    @Test
    void testAntragErstellenMitVorhandenemAntrag() {
        String matrikelnummer = "12345678";
        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer))
                .thenReturn(Optional.of(new Praktikumsantrag()));

        assertThrows(IllegalArgumentException.class, () -> praktikumsantragService.antragErstellen(matrikelnummer));
        verify(praktikumsantragRepository, times(1)).findByMatrikelnummer(matrikelnummer);
    }

    @Test
    void testAntragErstellenMitNullMatrikelnummer() {
        String matrikelnummer = null;
        assertThrows(IllegalArgumentException.class, () -> praktikumsantragService.antragErstellen(matrikelnummer));
    }
    @Test
    void testAntragErstellenMitLeererMatrikelnummer() {
        String matrikelnummer = "  ";

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> praktikumsantragService.antragErstellen(matrikelnummer));

        // Fehlermeldung
        assertEquals("Die Matrikelnummer darf nicht leer sein.", exception.getMessage());
    }

    @Test
    void testAntragAnzeigenErfolgreich() {
        String matrikelnummer = "12345678";
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer))
                .thenReturn(Optional.of(antrag));

        Praktikumsantrag result = praktikumsantragService.antragAnzeigen(matrikelnummer);

        assertEquals(antrag, result);
        verify(praktikumsantragRepository, times(1)).findByMatrikelnummer(matrikelnummer);
    }

    @Test
    void testAntragAnzeigenNichtVorhanden() {
        String matrikelnummer = "12345678";
        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> praktikumsantragService.antragAnzeigen(matrikelnummer));
        verify(praktikumsantragRepository, times(1)).findByMatrikelnummer(matrikelnummer);
    }

    @Test
    void testAntragAnzeigenMitNullMatrikelnummer() {
        String matrikelnummer = null;
        assertThrows(RuntimeException.class, () -> praktikumsantragService.antragAnzeigen(matrikelnummer));
    }


    @Test
    void testAntragUebermittelnMitNullwerten() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setNameStudentin(null);

        assertThrows(ConstraintViolationException.class, () -> praktikumsantragService.antragUebermitteln(antrag));
    }

    @Test
    void testAntragUebermittelnMitLeerwerten() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setNameStudentin("");

        assertThrows(ConstraintViolationException.class, () -> praktikumsantragService.antragUebermitteln(antrag));
    }

    @Test
    void testAntragUebermittelnMitMinWert() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setHausnummerStudentin(0);

        assertThrows(ConstraintViolationException.class, () -> praktikumsantragService.antragUebermitteln(antrag));
    }

    @Test
    void testAntragUebermittelnMitMaxWert() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setHausnummerStudentin(1000);

        assertThrows(ConstraintViolationException.class, () -> praktikumsantragService.antragUebermitteln(antrag));
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
        String matrikelnummer = "12345678";
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setMatrikelnummer(matrikelnummer);

        when(praktikumsantragRepository.findByMatrikelnummer(matrikelnummer)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> praktikumsantragService.antragUebermitteln(antrag));

        assertEquals("Kein Antrag mit der Matrikelnummer " + matrikelnummer + " gefunden.", exception.getMessage());
    }

    @Test
    void testAntragUebermittelnFalscherStatus() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setStatusAntrag(StatusAntrag.ABSOLVIERT); // Nicht GESPEICHERT

        when(praktikumsantragRepository.findByMatrikelnummer(antrag.getMatrikelnummer()))
                .thenReturn(Optional.of(antrag));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> praktikumsantragService.antragUebermitteln(antrag));

        assertEquals("Nur gespeicherte Anträge können übermittelt werden.", exception.getMessage());
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



    @Test
    void testAntragLoeschenErfolgreich() {
        Long id = 1L;
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setAntragsID(id);

        //MockTeil:
        when(praktikumsantragRepository.findById(id)).thenReturn(Optional.of(antrag));
        doNothing().when(praktikumsantragRepository).deleteById(id);

        praktikumsantragService.antragLoeschen(id);

        verify(praktikumsantragRepository, times(1)).findById(id);
        verify(praktikumsantragRepository, times(1)).deleteById(id);
    }

    @Test
    void testAntragLoeschenNichtVorhanden() {
        Long id = 22L;

        when(praktikumsantragRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> praktikumsantragService.antragLoeschen(id));

        assertEquals("Praktikumsantrag mit der ID: " + id + " ist nicht vorhanden und kann nicht gelöscht werden",
                exception.getMessage());

        verify(praktikumsantragRepository, times(1)).findById(id);
        verify(praktikumsantragRepository, times(0)).deleteById(id);
    }

    @Test
    @SuppressWarnings("ConstantConditions") // warning ignorieren
    void testAntragLoeschenMitNullId() {
        Long id = null;

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> praktikumsantragService.antragLoeschen(id));

        assertEquals("Praktikumsantrag mit der ID: null ist nicht vorhanden und kann nicht gelöscht werden",
                exception.getMessage());

        verify(praktikumsantragRepository, times(1)).findById(id);
        verify(praktikumsantragRepository, times(0)).deleteById(id);
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

}

