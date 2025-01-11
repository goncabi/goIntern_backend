package com.example.application.repositories;

import com.example.application.models.Sicherheitsfrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository-Schnittstelle für den Zugriff auf die gespeicherten Sicherheitsfragen.
 * <p>
 * Diese Schnittstelle ermöglicht CRUD-Operationen sowie spezielle Datenbankabfragen
 * für die Entität {@link Sicherheitsfrage}. Sie erweitert {@link JpaRepository},
 * um eine Vielzahl von Standardmethoden bereitzustellen.
 * </p>
 *
 */
@Repository
public interface SicherheitsfrageRepository extends JpaRepository<Sicherheitsfrage, Long> {
}
