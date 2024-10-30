package com.example.application.services;

import com.example.application.models.Praktikum;
import org.springframework.stereotype.Service;
import com.example.application.repositories.PraktikumRepository;

import java.util.List;

@Service
public class PraktikumService {
    private final PraktikumRepository repository;

    public PraktikumService(PraktikumRepository repository) {
        this.repository = repository;
    }

    public List<Praktikum> getAllPraktika() {
        return repository.findAll();
    }

    public Praktikum savePraktikum(Praktikum praktikum) {
        return repository.save(praktikum);
    }

    // Weitere Methoden entsprechend der Anforderung im Klassendiagramm
}