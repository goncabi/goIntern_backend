package com.example.application.services;

import com.example.application.models.Sicherheitsfrage;
import com.example.application.repositories.SicherheitsfrageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SicherheitsfragenService implements CommandLineRunner {
    @Autowired
    SicherheitsfrageRepository sicherheitsfrageRepo;

    @Override
    public void run(String... args) throws Exception {
        sicherheitsfrageRepo.saveAll(List.of(
                new Sicherheitsfrage(1, "Wie lautet dein Geburtsort?"),
                new Sicherheitsfrage(2, "Was war dein erstes Haustier?"),
                new Sicherheitsfrage(3, "Wie lautet der Name deiner Grundschule?")
        ));
    }
}
