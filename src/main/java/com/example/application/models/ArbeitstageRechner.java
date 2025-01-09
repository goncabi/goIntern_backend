package com.example.application.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;

import java.util.Set;



public class ArbeitstageRechner {

    LocalDate startDate;
    LocalDate endDate;
    boolean workingDaysAreEnough;
    boolean fourWorkingDays;
    boolean fiveWorkingDays;


    // Methode zur Prüfung, ob ein Datum ein Feiertag ist
    public int berechneFeiertageInZeitraum(String bundesland, LocalDate startDate, LocalDate endDate) {

        // HolidayManager Dependency für Deutschland
        HolidayManager manager = HolidayManager.getInstance(HolidayCalendar.GERMANY);

        // Alle Feiertage für jahr und Bundesland berechnen
        Set<Holiday> feiertage = manager.getHolidays(startDate.getYear(), bundesland);

        // Zähle die Feiertage, die im angegebenen Zeitraum liegen
        long feiertagsAnzahl = feiertage.stream()
                .filter(holiday -> !holiday.getDate().isBefore(startDate) && !holiday.getDate().isAfter(endDate))
                .count();

        return (int) feiertagsAnzahl;
    }


    public static int berechneArbeitstageMitFuenfTageWoche(LocalDate startDate, LocalDate endDate, String bundesland) {
        int workingDays = 0;
        ArbeitstageRechner rechner = new ArbeitstageRechner();

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


    public static int berechneArbeitstageMitVierTageWoche(LocalDate startDate, LocalDate endDate) {
        int workingDays = 0;

        //wochen zählen, dann mal vier, weil vier tage woche
        long wochen = ChronoUnit.WEEKS.between(startDate, endDate.plusDays(1));

        // Arbeitstage für eine 4-Tage-Woche berechnen
        workingDays = (int) wochen * 4;

        return workingDays;
    }

    public boolean workingDaysAreEnough(int workingDays) {
        return workingDays >= 75;
    }

}
