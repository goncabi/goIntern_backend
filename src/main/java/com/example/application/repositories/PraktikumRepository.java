package com.example.application.repositories;


import com.example.application.models.Praktikum;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PraktikumRepository extends JpaRepository<Praktikum, Long> {
    // Custom Query-Methode, um Praktika nach Status zu finden
    List<Praktikum> findByStatus(String status);
}