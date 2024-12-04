package com.example.application.services.registrierung;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrikelnummerValidiererTest {

    private final MatrikelnummerValidierer matrikelnummerValidierer = new MatrikelnummerValidierer();

    @Test
    void testMatrikelnummerstimmtUeberein(){

        String matrikelnummer = "s0123458";

        boolean isValid = matrikelnummerValidierer.isMatrikelnummerValid(matrikelnummer);
        assertTrue(isValid);
    }

    @Test
    void testMatrikelnummerOhneSubstring(){

        String matrikelnummer = "11123458";

        boolean isValid = matrikelnummerValidierer.isMatrikelnummerValid(matrikelnummer);
        assertFalse(isValid);
    }

    @Test
    void testMatrikelnummerMatrikelnummerZuKurz(){

        String matrikelnummer = "s01234";

        boolean isValid = matrikelnummerValidierer.isMatrikelnummerValid(matrikelnummer);
        assertFalse(isValid);
    }

    @Test
    void testMatrikelnummerMatrikelNummerZuLang(){
        String matrikelnummer = "s01234589";

        boolean isValid = matrikelnummerValidierer.isMatrikelnummerValid(matrikelnummer);
        assertFalse(isValid);
    }

    @Test
    void testMatrikelnummerLeererString(){
        String matrikelnummer = "";

        boolean isValid = matrikelnummerValidierer.isMatrikelnummerValid(matrikelnummer);
        assertFalse(isValid);
    }


    @Test
    void testMatrikelnummerNull() {
        assertFalse(matrikelnummerValidierer.isMatrikelnummerValid(null));
    }

}