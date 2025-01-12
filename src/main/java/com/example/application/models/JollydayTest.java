package com.example.application.models;

import de.jollyday.HolidayManager;
import de.jollyday.HolidayCalendar;

public class JollydayTest {
    public static void main(String[] args) {
        try {
            HolidayManager manager = HolidayManager.getInstance(HolidayCalendar.GERMANY);
            System.out.println("Feiertage geladen: " + manager.getHolidays(2025, "NW"));
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
