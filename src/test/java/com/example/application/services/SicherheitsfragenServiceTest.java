package com.example.application.services;

import com.example.application.models.Sicherheitsfrage;
import com.example.application.repositories.SicherheitsfrageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class SicherheitsfragenServiceTest {

    @Autowired
    private SicherheitsfragenService sicherheitsfragenService;

    @MockBean
    private SicherheitsfrageRepository sicherheitsfrageRepository;

    @Test
    void testRun() throws Exception {
        sicherheitsfragenService.run();

        verify(sicherheitsfrageRepository, atLeastOnce()).saveAll(List.of(
                new Sicherheitsfrage(1, "Wie lautet dein Geburtsort?"),
                new Sicherheitsfrage(2, "Was war dein erstes Haustier?"),
                new Sicherheitsfrage(3, "Wie lautet der Name deiner Grundschule?")
        ));
    }
    @Test

    void testRunThrowsException(){

        doThrow(new RuntimeException("Fehler beim Speichern"))
                .when(sicherheitsfrageRepository).saveAll(anyList());

        assertThrows(RuntimeException.class, () -> sicherheitsfragenService.run());
    }

}
