package com.example.application.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;

import java.util.Set;


//NOTIZ: ES MÜSSEN 600 ARBEITSSTUNDEN SEIN -> 15 wochen, also 75 Tage

public class ArbeitstageRechner {

    public static int berechneArbeitstage(String bundesland, String startDatum, String endDatum) {
        try {
        // Deutsches Datumsformat
        DateTimeFormatter germanFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate startDate = LocalDate.parse(startDatum, germanFormatter);
        LocalDate endDate = LocalDate.parse(endDatum, germanFormatter);

        HolidayManager manager = HolidayManager.getInstance(HolidayCalendar.GERMANY);
        int arbeitstage = 0;

        // Iteration über den Zeitraum
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            // Prüfen, ob der Tag ein Arbeitstag ist (Montag bis Freitag)
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                arbeitstage++;
            }

            // Prüfen, ob der Tag ein Feiertag ist
            int year = date.getYear();
            Set<Holiday> feiertage = manager.getHolidays(year, bundesland);
            LocalDate finalDate = date;
            if (feiertage.stream().anyMatch(holiday -> holiday.getDate().equals(finalDate))) {
                arbeitstage--;
            }
        }

        return arbeitstage;
    }
        catch (Exception e) {
        throw new RuntimeException(e);
    }



