package com.example.application.services;

import com.example.application.models.Praktikumsstelle;
import com.example.application.repositories.PraktikumsstelleRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class PraktikumsstelleService {
    private final PraktikumsstelleRepository repository;

    public PraktikumsstelleService(PraktikumsstelleRepository repository) {
        this.repository = repository;
    }

    public List<Praktikumsstelle> getAllPraktika() {
        return repository.findAll();
    }

    public Praktikumsstelle savePraktikum(Praktikumsstelle praktikum) {
        return repository.save(praktikum);
    }

    // Weitere Methoden entsprechend der Anforderung im Klassendiagramm
}