package com.example.application.repositories;

import com.example.application.models.Benachrichtigung;
import com.example.application.models.BenachrichtigungWichtigkeit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository-Schnittstelle für den Zugriff auf Benachrichtigungsdaten.
 * <p>
 * Diese Schnittstelle ermöglicht CRUD-Operationen sowie spezielle Datenbankabfragen
 * für die Entität {@link Benachrichtigung}. Sie erweitert {@link JpaRepository},
 * um eine Vielzahl von Standardmethoden bereitzustellen.
 * </p>
 *
 * @author Noa Sauter
 */
@Repository
public interface BenachrichtigungRepository extends JpaRepository<Benachrichtigung, Long> {
    /**
     * Findet alle Benachrichtigungen für einen bestimmten Empfänger und sortiert sie nach dem Datum aufsteigend.
     *
     * @param empfaenger Der Benutzername des Empfängers, für den die Benachrichtigungen abgerufen werden sollen.
     * @return Eine Liste von Benachrichtigungen, die dem angegebenen Empfänger zugeordnet sind,
     *         sortiert nach dem Datum in aufsteigender Reihenfolge.
     */
    List<Benachrichtigung> findByEmpfaengerOrderByDatumDesc(String empfaenger);

    /**
     * Findet alle Benachrichtigungen eines bestimmten Empfängers selektiert nach ihrer Wichtigkeit.
     * @param wichtigkeit Wichtigkeit der Nachricht, entweder wichtig oder unwichtig
     * @return Liste mit allen Nachrichten, die die entsprechende Wichtigkeit haben und dem Empfänger zugeordnet sind
     */
    List<Benachrichtigung> findByWichtigkeitAndEmpfaenger(BenachrichtigungWichtigkeit wichtigkeit, String empfaenger);

    /**
     * Findet alle Benachrichtigungen selektiert nach ihrer Wichtigkeit.
     * @param wichtigkeit Wichtigkeit der Nachricht, entweder wichtig oder unwichtig
     * @return Liste mit allen Nachrichten, die die entsprechende Wichtigkeit haben
     */
    List<Benachrichtigung> findByWichtigkeit(BenachrichtigungWichtigkeit wichtigkeit);

}
