package com.example.application.repositories;

import com.example.application.models.Sicherheitsantwort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository-Schnittstelle für den Zugriff auf die gespeicherten Sicherheitsantworten.
 * <p>
 * Diese Schnittstelle ermöglicht CRUD-Operationen sowie spezielle Datenbankabfragen
 * für die Entität {@link Sicherheitsantwort}. Sie erweitert {@link JpaRepository},
 * um eine Vielzahl von Standardmethoden bereitzustellen.
 * </p>
 *
 */
@Repository
public interface SicherheitsantwortRepository extends JpaRepository<Sicherheitsantwort, Long> {
    /**
     * Findet die Sicherheitsantwort einer bestimmten Studentin.
     * @param matrikelnummer Die Matrikelnummer der Studentin, für die die Sicherheitsantwort abgerufen werden soll.
     * @return Ein Optional der Sicherheitsantwort, die der angegebenen Matrikelnummer zugehörig ist.
     */
    Optional<Sicherheitsantwort> findByMatrikelnummer(String matrikelnummer);
}
