package com.example.application.services;

import com.example.application.models.Praktikumsantrag;
import com.example.application.repositories.PraktikumsantragRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Locale;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MockDataServiceTest {

    @Mock
    private PraktikumsantragRepository praktikumsantragRepository;

    @InjectMocks
    private MockDataService mockDataService;

    @Test
    void testGenerateMockData() {
        int count = 5;

        mockDataService.generateMockData(count);

        verify(praktikumsantragRepository, times(count)).save(any(Praktikumsantrag.class));
    }
}