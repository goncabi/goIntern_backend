package com.example.application.repositories;

import com.example.application.models.Praktikumsantrag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository-Schnittstelle für den Zugriff auf Praktikumsantragsdaten.
 * <p>
 * Diese Schnittstelle ermöglicht CRUD-Operationen sowie spezielle Datenbankabfragen
 * für die Entität {@link Praktikumsantrag}. Sie erweitert {@link JpaRepository},
 * um eine Vielzahl von Standardmethoden bereitzustellen.
 * </p>
 *
 */
@Repository
public interface PraktikumsantragRepository extends JpaRepository<Praktikumsantrag, Long> {
        /**
         * Findet den Praktikumsantrag einer bestimmten Studentin.
         *
         * @param matrikelnummer Die Matrikelnummer der Studentin für die der Antrag abgerufen werden soll.
         * @return Optional des Praktikumsantrages, der der angegebenen Matrikelnummer zugehörig ist.
         */
        Optional<Praktikumsantrag> findByMatrikelnummer(String matrikelnummer);
}
/*Mit Spring Data JPA bieten Repositories sofortigen Zugriff auf Standard-CRUD-Operationen wie save, findAll, findById, und delete,
 ohne dass zusätzlicher Code geschrieben werden muss. Spring Data bietet sogar erweiterte Methoden zur Abfrage, die einfach
  durch das Benennen von Methoden wie findByName oder findByEmail erstellt werden. Also voll PRAKTISCH!
  Die Service-Schicht können sich auf die Geschäftslogik konzentrieren, ohne sich um die Details des Datenzugriffs kümmern zu müssen.
  Methoden:
  T save(T entity)
Speichert oder aktualisiert eine Entität in der Datenbank.

Optional<T> findById(ID id)
Sucht eine Entität anhand ihres Primärschlüssels (ID).

boolean existsById(ID id)
Prüft, ob eine Entität mit der angegebenen ID existiert.

List<T> findAll()
Gibt alle Entitäten eines bestimmten Typs zurück.

List<T> findAllById(Iterable<ID> ids)
Gibt alle Entitäten mit den angegebenen IDs zurück.

long count()
Gibt die Anzahl aller Entitäten in der Tabelle zurück.

void deleteById(ID id)
Löscht die Entität mit der angegebenen ID.

void delete(T entity)
Löscht eine bestimmte Entität.

void deleteAll(Iterable<? extends T> entities)
Löscht mehrere Entitäten.

void deleteAll()
Löscht alle Entitäten in der Tabelle.

*/
