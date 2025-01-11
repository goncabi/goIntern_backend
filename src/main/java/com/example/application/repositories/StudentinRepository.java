package com.example.application.repositories;

import com.example.application.models.Studentin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository-Schnittstelle für den Zugriff auf die Studentinnendaten.
 * <p>
 * Diese Schnittstelle ermöglicht CRUD-Operationen sowie spezielle Datenbankabfragen
 * für die Entität {@link Studentin}. Sie erweitert {@link JpaRepository},
 * um eine Vielzahl von Standardmethoden bereitzustellen.
 * </p>
 */
@Repository
public interface StudentinRepository extends JpaRepository<Studentin, String> {
    /**
     * Findet die Studentin mit der angegebenen Matrikelnummer.
     * @param matrikelnummer Die Matrikelnummer der gesuchten Studentin.
     * @return Optional der Studentin, die der angegebenen Matrikelnummer zugehörig ist.
     */
    Optional<Studentin> findByMatrikelnummer(String matrikelnummer);
}