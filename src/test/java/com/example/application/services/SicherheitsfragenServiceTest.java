package com.example.application.services;

import com.example.application.repositories.SicherheitsfrageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class SicherheitsfragenServiceTest {
@Autowired
private SicherheitsfragenService sicherheitsfragenService;

@MockBean
private SicherheitsfrageRepository sicherheitsfrageRepository;

    @Test
    void testRun() {


    }
}