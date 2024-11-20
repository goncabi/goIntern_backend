package com.example.application.services;

import com.example.application.models.Praktikumsantrag;
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

    Praktikumsantrag antrag = new Praktikumsantrag();

    private Praktikumsantrag erzeugeGueltigenAntrag() {

        antrag.setAntragsID(111L);
        antrag.setNameStudentin("Hunt");
        antrag.setVornameStudentin("Maria");
        antrag.setGebDatumStudentin(LocalDate.of(1999, 3, 11));
        antrag.setStrasseStudentin("Friedrichstr.");
        antrag.setHausnummerStudentin(34);
        antrag.setPlzStudentin(10598);
        antrag.setOrtStudentin("Berlin");
        antrag.setTelefonnummerStudentin("01478112530");
        antrag.setEmailStudentin("mariahuna@gmail.com");
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
        return antrag;
    }

    @Test
    void testAntragMitNullwerten() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setNameStudentin(null);

        assertThrows(ConstraintViolationException.class, () -> praktikumsantragService.antragStellen(antrag));
    }

    @Test
    void testAntragMitLeerwerten() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setNameStudentin("");

        assertThrows(ConstraintViolationException.class, () -> praktikumsantragService.antragStellen(antrag));
    }

    @Test
    void testAntragMitMinWert() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setHausnummerStudentin(0);

        assertThrows(ConstraintViolationException.class, () -> praktikumsantragService.antragStellen(antrag));
    }

    @Test
    void testAntragMitMaxWert() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setHausnummerStudentin(1000);

        assertThrows(ConstraintViolationException.class, () -> praktikumsantragService.antragStellen(antrag));
    }

    @Test
    void testAntragMitGueltigenDaten() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();

        when(praktikumsantragRepository.save(antrag)).thenReturn(antrag); // Mock save method
        String result = praktikumsantragService.antragStellen(antrag); // Call service
        assertEquals("Antrag erfolgreich angelegt.", result);
    }

//    @Test
//    public void testAntragStellen_NeuerAntrag() {
//        when(praktikumsantragRepository.findByMatrikelnummer(anyString())).thenReturn(Optional.empty());
//
//        String result = praktikumsantragService.antragStellen();
//
//        assertEquals("Antrag erfolgreich angelegt.", result);
//        verify(praktikumsantragRepository, times(1)).save(this.antrag);
//    }

//    @Test
//    void antragVorhandenButtonUndBereitsVorhanden() {
//        boolean result = antrag.antragVorhanden(s0123456);
//        assertTrue(result);
//    }
//    @Test
//    void antragVorhandenButtonUndNochNichtVorhanden() {
//        boolean result = antrag.antragVorhanden(s0123456);
//        assertFalse(result);
//    }
//
//
@Test
void testAntragLoeschenErfolgreich() {
    Long id = 1L;
    Praktikumsantrag antrag = erzeugeGueltigenAntrag();
    antrag.setAntragsID(id);

    //MockTeil: praktikumRepo soll gemockt werden:
    //Die beiden Zeilen sagen dass die Datenbank umgangen werden soll und dass bei findById ein optional returnt werden soll und bei delteID soll nix passieren (weil beim test soll nicht wirlich was gelöscht werden in der datenbank)
    when(praktikumsantragRepository.findById(id)).thenReturn(Optional.of(antrag)); // Ein Optional antrag wird erzeugt und zurück gegeben
    doNothing().when(praktikumsantragRepository).deleteById(id);

    //hier wird die loschenMethode aufgerufen und der antrag mit der id wird gelöscht
    praktikumsantragService.antragLoeschen(id);

    //ähnlich wie assert. Hier wird getestet wie oft findyById aufgerufen wird und wie oft die ID gelöscht wird
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


}
