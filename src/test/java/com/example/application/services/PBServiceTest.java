package com.example.application.services;

import com.example.application.models.AppUserRole;
import com.example.application.models.Praktikumsbeauftragter;
import com.example.application.repositories.PBRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void testRun() throws Exception {
        Praktikumsbeauftragter mockPB = new Praktikumsbeauftragter("Jörn Freiheit", "AbInDieFreiheit13579!", AppUserRole.ADMIN, "Informatik und Wirtschaft");

        pbService.run();

        verify(pBRepository, atLeastOnce()).save(mockPB);
    }

    @Test
    void testRunThrowsException() {
        doThrow(new RuntimeException("Fehler beim Speichern"))
                .when(pBRepository).save(any(Praktikumsbeauftragter.class));

        assertThrows(RuntimeException.class, () -> pbService.run());
    }

}
