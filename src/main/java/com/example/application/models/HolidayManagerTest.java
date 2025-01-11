package com.example.application.models;

import de.jollyday.HolidayManager;


public class HolidayManagerTest {
    public static void main(String[] args) {
        try {
            HolidayManager manager = HolidayManager.getInstance("de"); // Für Deutschland
            System.out.println("HolidayManager erfolgreich initialisiert.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
