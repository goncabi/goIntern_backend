package com.example.application.repositories;

import com.example.application.models.Sicherheitsfrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SicherheitsfrageRepository extends JpaRepository<Sicherheitsfrage, Long> {
}
