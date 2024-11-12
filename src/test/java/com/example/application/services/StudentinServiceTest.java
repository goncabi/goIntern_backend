package com.example.application.services;

import com.example.application.models.Studentin;
import com.example.application.repositories.StudentinRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class StudentinServiceTest {

    @Autowired
    private StudentinService studentinService;

    @MockBean
    private StudentinRepository studentinRepository;

    @Test
    void testSignUpUserMatrikelnummerNeu(){
        Studentin studentin = new Studentin();
        studentin.setMatrikelnummer("123456");
        studentinService.signUpUser(studentin);

        when(studentinRepository.findByMatrikelnummer(studentin.getMatrikelnummer()))
                .thenReturn(Optional.empty());

        verify(studentinRepository, times(1)).save(studentin);
    }

    @Test
    void testSignUpUserMatrikelnummerBekannt(){
        Studentin studentin = new Studentin();
        studentin.setMatrikelnummer("123476");
        studentinService.signUpUser(studentin);

        when(studentinRepository.findByMatrikelnummer(studentin.getMatrikelnummer()))
                .thenReturn(Optional.of(studentin));

        assertThrows(IllegalStateException.class, () -> studentinService.signUpUser(studentin));
    }



}