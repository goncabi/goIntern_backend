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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@Transactional
@Validated

class PraktikumsantragServiceTest {


    @Autowired
    private PraktikumsantragService praktikumsantragService;

    @MockBean
    private PraktikumsantragRepository praktikumsantragRepository;


    private Praktikumsantrag erzeugeGueltigenAntrag() {
        Praktikumsantrag antrag = new Praktikumsantrag();
        antrag.setAntragsID(111L);
        antrag.setNameStudentin("Hunt");
        antrag.setVornameStudentin("Maria");
        antrag.setGebDatumStudentin(LocalDate.of(1999, 3, 11));
        antrag.setStrasseStudentin("Friedrichstr.");
        antrag.setHausnummerStudentin(34);
        antrag.setPlzStudentin(10598);
        antrag.setOrtStudentin("Berlin");
        antrag.setTelefonnummerStudentin("01478112530");
        antrag.setEmailStudentin("mariajuana@gmail.com");
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
        antrag.setNameStudentin(null);  // Pflichtfeld auf null setzen

        assertThrows(ConstraintViolationException.class, () -> praktikumsantragService.antragSpeichern(antrag));
    }

    @Test
    void testAntragMitLeerwerten() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setNameStudentin("");  // Pflichtfeld leer setzen

        assertThrows(ConstraintViolationException.class, () -> praktikumsantragService.antragSpeichern(antrag));
    }

    @Test
    void testAntragMitMinWert() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setHausnummerStudentin(0);  // Unterer Grenzwert

        assertThrows(ConstraintViolationException.class, () -> praktikumsantragService.antragSpeichern(antrag));
    }

    @Test
    void testAntragMitMaxWert() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();
        antrag.setHausnummerStudentin(1000);  // Oberer Grenzwert überschritten

        assertThrows(ConstraintViolationException.class, () -> praktikumsantragService.antragSpeichern(antrag));
    }

    @Test
    void testAntragMitGueltigenDaten() {
        Praktikumsantrag antrag = erzeugeGueltigenAntrag();

        when(praktikumsantragRepository.save(antrag)).thenReturn(antrag);

        Praktikumsantrag result = praktikumsantragService.antragSpeichern(antrag);
        assertEquals(antrag, result);
    }
}