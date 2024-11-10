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
                new Sicherheitsfrage(1,"Wie lautet der Geburtsname Ihrer Mutter?"),
                new Sicherheitsfrage(2,"Wie viele Haustiere hatten Sie, als Sie 10 Jahre alt waren?"),
                new Sicherheitsfrage(3,"Wie lautete der Spitzname deines/r besten Freundes/in in der Kindheit?")
        ));
    }
}
