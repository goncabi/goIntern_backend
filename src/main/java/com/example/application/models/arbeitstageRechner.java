package com.example.application.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
//import de.jollyday.HolidayCalendar;
//import de.jollyday.HolidayManager;
//import de.jollyday.Holiday;
import java.util.Set;



public class arbeitstageRechner {

    LocalDate startDate;
    LocalDate endDate;


//    // Methode zur Prüfung, ob ein Datum ein Feiertag ist
//    public static boolean istFeiertag(LocalDate datum, String bundesland) {
//        // HolidayManager für Deutschland
//        HolidayManager manager = HolidayManager.getInstance(HolidayCalendar.GERMANY);
//
//        // Alle Feiertage im gegebenen Jahr und Bundesland abrufen
//        Set<Holiday> feiertage = manager.getHolidays(datum.getYear(), bundesland);
//
//        // Prüfen, ob das Datum ein Feiertag ist
//        return feiertage.stream().anyMatch(holiday -> holiday.getDate().equals(datum));
//    }


    public static int calculateWith5WorkingDaysInPeriod(LocalDate startDate, LocalDate endDate) {
        int workingDays = 0;

        // Schleife durch die Tage im Zeitraum

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            // Überprüfen, ob der Tag ein Wochentag (Montag bis Freitag) ist
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workingDays++;
            }
        }

        return workingDays;
    }


    public static int calculateWith4WorkingDaysInPeriod(LocalDate startDate, LocalDate endDate) {
        int workingDays = 0;

        // Schleife durch die Tage im Zeitraum

        long wochen = ChronoUnit.WEEKS.between(startDate, endDate.plusDays(1));

        // Arbeitstage für eine 4-Tage-Woche berechnen
        workingDays = (int) wochen * 4;


        return workingDays;
    }

    public void workingDaysAreEnough(int workingDays) {

        if(workingDays < 75) {
            System.out.println("Angegebener Zeitraum reicht nicht aus");
        }
        else {
            System.out.println("Angegebener Zeitraum ist im Bereich der 600 Arbeitsstunden");
        }
    }


//    public static void main(String[] args) {
//
//        arbeitstageRechner arbeitstageRechner = new arbeitstageRechner();
//
//        LocalDate startDate = LocalDate.of(2024, 1, 1);
//        LocalDate endDate = LocalDate.of(2024, 12, 31);
//        int workingDaysInPeriod1 = calculateWith5WorkingDaysInPeriod(startDate, endDate);
//        System.out.println("Arbeitstage bei einer 5-Tage-Woche: " + workingDaysInPeriod1);
//        arbeitstageRechner.workingDaysAreEnough(workingDaysInPeriod1);
//        System.out.println("");
//
//        int workingDaysInPeriod2 = calculateWith4WorkingDaysInPeriod(startDate, endDate);
//        System.out.println("Arbeitstage bei einer 4-Tage-Woche: " + workingDaysInPeriod2);
//        arbeitstageRechner.workingDaysAreEnough(workingDaysInPeriod2);
//        System.out.println("");
//
//
//        LocalDate startDate2 = LocalDate.of(2024, 8, 1);
//        LocalDate endDate2 = LocalDate.of(2024, 9, 30);
//        int workingDaysInPeriod3 = calculateWith5WorkingDaysInPeriod(startDate2, endDate2);
//        System.out.println("Arbeitstage bei einer 5-Tage-Woche: " +workingDaysInPeriod3);
//        arbeitstageRechner.workingDaysAreEnough(workingDaysInPeriod3);
//        System.out.println("");

  //      LocalDate datum = LocalDate.of(2024, 10, 3); // Beispiel: Tag der Deutschen Einheit

//        // Beispiel-Bundesland: Baden-Württemberg
//        boolean istFeiertag = istFeiertag(datum, "bw"); // bw für Baden-Württemberg

 //       System.out.println("Ist der " + datum + " ein Feiertag in Baden-Württemberg? " + istFeiertag);




 //   }


}
