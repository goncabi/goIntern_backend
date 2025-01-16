package com.example.application.repositories;

import com.example.application.models.Praktikumsbeauftragter;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.application.models.AppUserRole;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository-Schnittstelle für den Zugriff auf die Praktikumsbeauftragtendaten.
 * <p>
 * Diese Schnittstelle ermöglicht CRUD-Operationen sowie spezielle Datenbankabfragen
 * für die Entität {@link Praktikumsbeauftragter}. Sie erweitert {@link JpaRepository},
 * um eine Vielzahl von Standardmethoden bereitzustellen.
 * </p>
 */

// das Jpa Repo von dem geerbt wird bringt z.b. auch delete Methoden etc. mit
@Repository
public interface PBRepository extends JpaRepository<Praktikumsbeauftragter, Long> {
    /**
     * Findet den Praktikumsbeauftragten mit dem übergebenen Benutzernamen(username).
     * @param username Der Benutzername des gesuchten Praktikumsbeauftragten.
     * @return Ein Optional des Praktikumsbeauftragten, dem der Benutzername zugehörig ist.
     */
    Optional<Praktikumsbeauftragter> findByUsername(String username);

    /**
     * Findet den Praktikumsbeauftragten mit der übergebenen AppUserRole.
     * @param appUserRole Die Rolle, die ein Benutzer in der Anwendung zugewiesen bekommen hat.
     * @return Ein Optional des Praktikumsbeauftragten, der die angegebene AppUserRole hat.
     */
    Optional<Praktikumsbeauftragter> findByUserRole(AppUserRole appUserRole);
}
