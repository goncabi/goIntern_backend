package com.example.application.services;

import com.example.application.models.Praktikumsantrag;
import com.example.application.models.Status_Antrag;
import com.example.application.repositories.PraktikumsantragRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
class PraktikumsantragServiceTest {


    @Autowired
    private PraktikumsantragService praktikumsantragService;

    @MockBean
    private PraktikumsantragRepository praktikumsantragRepository;

    @Test
    void testAntragSpeicher(){
        Praktikumsantrag antrag = new Praktikumsantrag();
        antrag.setAntragsID(111L);
        antrag.setNameStudentin("Hunt");
        antrag.setVornameStudentin("Maria");
        antrag.setAbteilung("IT");
        antrag.setGebDatumStudentin(LocalDate.of(1999, 3, 11));
        antrag.setStrasseStudentin("Friedrichstr.");
        antrag.setHausnummerStudentin(34);
        antrag.setPlzStudentin(10598);
        antrag.setOrtStudentin("Berlin");
        antrag.setTelefonnummerStudentin("01478112530");
        antrag.setEmailStudentin("mariajuana@gmail.com");

        antrag.setVorschlagPraktikumsbetreuerIn("Ana Freigeist");
        antrag.setPraktikumssemester("WiSe2025");
        antrag.setStudiensemester(3);
        antrag.setStudiengang("Informatik");
        antrag.setBegleitendeLehrVeranstaltungen("Programmierung");

        antrag.setVoraussetzendeLeistungsnachweise(true);
        antrag.setFehlendeLeistungsnachweise("Datenbanken");
        antrag.setAusnahmeZulassung(true);

        antrag.setDatumAntrag(LocalDate.of(2024, 11, 15));
        antrag.setStatusAntrag(Status_Antrag.INBEARBEITUNG);

        antrag.setNamePraktikumsstelle("Tech Solutions");
        antrag.setStrassePraktikumsstelle("Hauptstrasse");
        antrag.setPlzPraktikumsstelle(10117);
        antrag.setOrtPraktikumsstelle("Berlin");
        antrag.setLandPraktikumsstelle("Deutschland");
        antrag.setAnsprechpartnerPraktikumsstelle("Herr Schmidt");
        antrag.setTelefonPraktikumsstelle("0123456789");
        antrag.setEmailPraktikumsstelle("contact@techsolutions.com");
        antrag.setAbteilung("IT");
        antrag.setTaetigkeit("Qualitätssischerung");
        antrag.setStartdatum(LocalDate.of(2024, 11, 15));
        antrag.setEnddatum(LocalDate.of(2024, 11, 16));

        // wie der mock sich verhält
        when(praktikumsantragRepository.save(antrag)).thenReturn(antrag);
        Praktikumsantrag result = praktikumsantragService.antragSpeichern(antrag);
        assertEquals(antrag, result);
    }
}
