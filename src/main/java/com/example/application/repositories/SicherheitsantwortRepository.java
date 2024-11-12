package com.example.application.repositories;

import com.example.application.models.Sicherheitsantwort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface SicherheitsantwortRepository extends JpaRepository<Sicherheitsantwort, Long> {
    Set<Sicherheitsantwort> findByMatrikelnummer(String matrikelnummer);
}
