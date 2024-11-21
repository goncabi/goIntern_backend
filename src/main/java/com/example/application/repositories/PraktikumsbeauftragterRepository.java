package com.example.application.repositories;

import com.example.application.models.Praktikumsbeauftragter;
import com.example.application.models.Studentin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PraktikumsbeauftragterRepository extends JpaRepository<Praktikumsbeauftragter, Long> {
    Optional<Praktikumsbeauftragter> findByUsername(String username);
}
