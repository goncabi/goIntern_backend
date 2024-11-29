package com.example.application.services;

import com.example.application.models.AppUserRole;
import com.example.application.models.Praktikumsantrag;
import com.example.application.models.Praktikumsbeauftragter;
import com.example.application.models.Status_Antrag;
import com.example.application.models.benachrichtigung.Benachrichtigung;
import com.example.application.models.benachrichtigung.LeseStatus;
import com.example.application.repositories.BenachrichtigungRepository;
import com.example.application.repositories.PBRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

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

/*    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pbService = new PBService(null, benachrichtigungRepository);
    }*/

    @Test
    void testRun() throws Exception {
        Praktikumsbeauftragter mockPB = new Praktikumsbeauftragter("Jörn Freiheit", "AbInDieFreiheit13579!", AppUserRole.ADMIN);

        pbService.run();

        verify(pBRepository, atLeastOnce()).save(mockPB);
    }

    @Test
    void testRunThrowsException() {
        doThrow(new RuntimeException("Fehler beim Speichern"))
                .when(pBRepository).save(any(Praktikumsbeauftragter.class));

        assertThrows(RuntimeException.class, () -> pbService.run());
    }

    @Test
    void testAntragGenehmigen() {
        // Vorbereitung
        Praktikumsantrag antrag = new Praktikumsantrag();
        antrag.setMatrikelnummer("123456");
        antrag.setStatusAntrag(Status_Antrag.INBEARBEITUNG);

        // Hier die Methode ausfuehren
        pbService.antragGenehmigen(antrag);

        // hier testen
        assertEquals(Status_Antrag.ZUGELASSEN, antrag.getStatusAntrag());
        verify(benachrichtigungRepository, times(1)).save(Mockito.any());
    }

    @Test
    void testAntragGenehmigenWithNullAntrag() {
        // Arrange
        Praktikumsantrag antrag = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> pbService.antragGenehmigen(antrag));
    }

}
