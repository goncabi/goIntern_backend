package com.example.application.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;

import java.util.Set;


//NOTIZ: ES MÜSSEN 600 ARBEITSSTUNDEN SEIN -> 15 wochen, also 75 Tage

public class ArbeitstageRechner {

    // Methode zur Prüfung, ob ein Datum ein Feiertag ist
    public int berechneFeiertageInZeitraum(String bundesland, LocalDate startDate, LocalDate endDate) {

        HolidayManager manager = HolidayManager.getInstance(HolidayCalendar.GERMANY);
        int feiertageAnzahl = 0;

        // Iteriert über die Jahre im zeitraum
        for (int year = startDate.getYear(); year <= endDate.getYear(); year++) {
            // Feiertage für das aktuelle Jahr und Bundesland abrufen
            Set<Holiday> feiertage = manager.getHolidays(year, bundesland);

            // Zählt Feiertage im angegebenen Zeitraum
            feiertageAnzahl += (int) feiertage.stream()
                    .filter(holiday -> !holiday.getDate().isBefore(startDate) && !holiday.getDate().isAfter(endDate))
                    .count();
        }
        return feiertageAnzahl;
    }


    public static int berechneArbeitstageMitFuenfTageWoche( String bundesland, LocalDate startDate, LocalDate endDate) {
        int workingDays = 0;
        ArbeitstageRechner rechner = new ArbeitstageRechner();

        System.out.println("Berechnung der Arbeitstage:");
        System.out.println("Startdatum: " + startDate);
        System.out.println("Enddatum: " + endDate);


        // Schleife durch die Tage im Zeitraum

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            // Überprüfen, ob der Tag ein Wochentag (Montag bis Freitag) ist
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workingDays++;
            }
        }
        // Feiertage im Zeitraum berechnen
        int feiertage = rechner.berechneFeiertageInZeitraum(bundesland, startDate, endDate);
        return workingDays - feiertage;
    }

}
