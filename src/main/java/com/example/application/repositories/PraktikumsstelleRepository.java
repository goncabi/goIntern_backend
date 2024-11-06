package com.example.application.repositories;


import com.example.application.models.Praktikumsstelle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PraktikumsstelleRepository extends JpaRepository<Praktikumsstelle, Long> {
    // Custom Query-Methode, um Praktika nach Status zu finden
    //List<Praktikumsstelle> findByStatus(String status);
}