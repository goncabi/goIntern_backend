package com.example.application.services;

import com.example.application.models.Sicherheitsfrage;
import com.example.application.repositories.SicherheitsfrageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SicherheitsfragenService bietet die Logik zur Verwaltung von Sicherheitsfragen.
 * <p>
 * Dieser Service ist als Spring-{@code @Service} deklariert, wodurch er als
 * Bean im Spring-Kontext verwendet werden kann. Er initialisiert eine Liste von
 * Sicherheitsfragen beim Start der Anwendung.
 * </p>
 *
 * <ul>
 *   <li>Speichert die Sicherheitsfragen in der Datenbank.</li>
 *   <li>Implementiert {@link CommandLineRunner}, um Code beim Start der Anwendung auszuführen.</li>
 * </ul>
 *
 * <h3>Funktionen:</h3>
 * <ul>
 *   <li>Initialisiert eine Liste vordefinierter Sicherheitsfragen.</li>
 *   <li>Speichert diese Fragen in einem Repository ({@link SicherheitsfrageRepository}).</li>
 * </ul>
 *
 * @author Beyza Nur Acikgöz
 * @version 1.0
 */

@Service
public class SicherheitsfragenService implements CommandLineRunner {

    /**
     * Repository für den Zugriff auf die Sicherheitsfragen-Daten.
     * <p>
     * Das {@link SicherheitsfrageRepository} wird von Spring automatisch
     * bereitgestellt und hier mit {@code @Autowired} injiziert.
     * </p>
     */

    @Autowired
    SicherheitsfrageRepository sicherheitsfrageRepo;

    /**
     * Initialisiert Sicherheitsfragen und speichert sie in der Datenbank.
     * <p>
     * Diese Methode wird beim Start der Anwendung automatisch aufgerufen,
     * da die Klasse {@code CommandLineRunner} implementiert.
     * </p>
     *
     * <h4>Beispiel-Fragen:</h4>
     * <ul>
     *   <li>"Wie lautet dein Geburtsort?"</li>
     *   <li>"Was war dein erstes Haustier?"</li>
     *   <li>"Wie lautet der Name deiner Grundschule?"</li>
     * </ul>
     *
     * @param args Argumente, die beim Start der Anwendung übergeben werden.
     * @throws Exception Wenn ein Fehler während der Initialisierung auftritt.
     */

    @Override
    public void run(String... args) throws Exception {
        sicherheitsfrageRepo.saveAll(List.of(
                new Sicherheitsfrage(1, "Wie lautet dein Geburtsort?"),
                new Sicherheitsfrage(2, "Was war dein erstes Haustier?"),
                new Sicherheitsfrage(3, "Wie lautet der Name deiner Grundschule?")
        ));
    }
}
