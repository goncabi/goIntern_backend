package com.example.application.repositories;

import com.example.application.models.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Das {@code PosterRepository} ist eine Schnittstelle zur Datenbankverwaltung von {@link Poster}-Entitäten.
 * Es erweitert {@link JpaRepository}, um CRUD-Operationen sowie benutzerdefinierte Abfragen bereitzustellen.
 *
 * <p>Hauptaufgaben:</p>
 * <ul>
 *     <li>CRUD-Operationen (Create, Read, Update, Delete) für die Datenbank. Also Speichern, Abrufen, Aktualisieren und Löschen von {@link Poster}-Entitäten.</li>
 *     <li>Bereitstellung einer benutzerdefinierten Methode zum Abrufen eines Posters basierend auf der Matrikelnummer.</li>
 * </ul>
 *
 * <p>Dieses Repository wird von Spring Data JPA verwaltet, wodurch die Implementierung der Methoden automatisch
 * generiert wird.</p>
 *
 * <h2>Abhängigkeiten</h2>
 * <p>Das Repository benötigt eine Verbindung zu einer relationalen Datenbank und verwendet die {@link Poster}-Entität,
 * die als JPA-Entity annotiert ist.</p>
 *
 * @author Maryam Mirza
 * @since 2025-01-29
 */

@Repository
public interface PosterRepository extends JpaRepository<Poster, Long> {

    /**
     *
     * Die Methode findByMatrikelnummer findet ein Poster basierend auf der Matrikelnummer der Studentin.
     *
     * <p>Diese Methode führt eine Abfrage aus, um ein {@link Poster} zu finden,
     * das mit der angegebenen Matrikelnummer verknüpft ist. Wenn kein Poster gefunden wird, gibt die Methode
     * ein leeres {@link Optional} zurück.</p>
     *
     * @param matrikelnummer Die Matrikelnummer der Studentin, für die das Poster gesucht wird.
     *                       Diese Matrikelnummer muss eindeutig sein.
     * @return Ein {@link Optional}, das entweder das gefundene {@link Poster} enthält oder leer ist,
     *         wenn kein entsprechender Eintrag gefunden wurde.
     */
 /*
  Mit findByMatrikelnummer kann ein Poster anhand der Matrikelnummer gefunden werden.
    Rückgabewert: Ein Optional<Poster> (leer, wenn kein Eintrag gefunden wurde).
  */
    Optional<Poster> findByMatrikelnummer(String matrikelnummer);
    boolean existsByMatrikelnummer(String matrikelnummer);

}
