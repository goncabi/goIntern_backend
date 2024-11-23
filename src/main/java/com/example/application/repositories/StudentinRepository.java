package com.example.application.repositories;

import com.example.application.models.Studentin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudentinRepository extends JpaRepository<Studentin, String> {
    Optional<Studentin> findByMatrikelnummer(String matrikelnummer);
}