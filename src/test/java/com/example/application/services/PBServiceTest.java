package com.example.application.services;

import com.example.application.models.Praktikumsbeauftragter;
import com.example.application.repositories.PraktikumsantragRepository;
import com.example.application.repositories.PBRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;


@SpringBootTest
@ActiveProfiles("test")
@Transactional

class PBServiceTest {

    @Autowired
    private PBService pbService;

    @MockBean
    private PBRepository pBRepository;
    Praktikumsbeauftragter praktikumsbeauftragter = new Praktikumsbeauftragter();

    @Test
    void testSignUpPB() {

        praktikumsbeauftragter.setPasswort("AbInDieFreiheit13579!");
        praktikumsbeauftragter.setUsername("Jörn Freiheit");
        pbService.signUpUser(praktikumsbeauftragter);

        Mockito.when(pBRepository.findByUsername(praktikumsbeauftragter.getUsername()))
                .thenReturn(Optional.empty());
        verify(pBRepository, times(1)).save(praktikumsbeauftragter);
    }



}