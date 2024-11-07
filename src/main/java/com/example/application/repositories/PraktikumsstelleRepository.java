package com.example.application.repositories;


import com.example.application.models.Praktikumsantrag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PraktikumsstelleRepository extends JpaRepository<Praktikumsantrag, Long> {
    // Custom Query-Methode, um Praktika nach Status zu finden
    //List<Praktikumsstelle> findByStatus(String status);
}