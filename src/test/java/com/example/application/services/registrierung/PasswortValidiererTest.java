package com.example.application.services.registrierung;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswortValidiererTest {

        private final PasswortValidierer passwortValidierer = new PasswortValidierer();

    @Test
    void testPasswoerterStimmenUeberein() {

        String password1 = "Passw0rd!";
        String password2 = "Passw0rd!";

        boolean isValid = passwortValidierer.passwordValidation(password1, password2);

        assertTrue(isValid, "Passwort gespeichert.");
    }

    @Test
    void testPasswoerterStimmenNichtUeberein() {

        String password1 = "Passw0rd!";
        String password2 = "blablabla";

        boolean isValid = passwortValidierer.passwordValidation(password1, password2);

        assertFalse(isValid, "Passwörter stimmen nicht überein.");
    }

    @Test
    void testPasswoerterZuKurz() {

        String password1 = "bla";
        String password2 = "bla";

        boolean isValid = passwortValidierer.passwordValidation(password1, password2);

        assertFalse(isValid, "Passwort ist zu kurz und nicht valide.");
    }

    @Test
    void testPasswortOhneSpecialCharacter() {

        String password1 = "Passwort123";
        String password2 = "Passwort123";

        boolean isValid = passwortValidierer.passwordValidation(password1, password2);

        assertFalse(isValid, "Sonderzeichen fehlt. Passwort invalide.");
    }

    @Test
    void testPasswortOhneZahlen() {

        String password1 = "Passwort!";
        String password2 = "Passwort!";

        boolean isValid = passwortValidierer.passwordValidation(password1, password2);

        assertFalse(isValid, "Zahl fehlt. Passwort invalide.");
    }

    @Test
    void testPasswortOhneBuchstaben() {

        String password1 = "1234567!";
        String password2 = "1234567!";

        boolean isValid = passwortValidierer.passwordValidation(password1, password2);

        assertFalse(isValid, "Buchstaben fehlen. Password invalide.");
    }

    @Test
    void testPasswortEnthaeltSonderzeichen() {

        String password1 = "Pässwort123!";
        String password2 = "Pässwort123!";

        boolean isValid = passwortValidierer.passwordValidation(password1, password2);

        assertTrue(isValid, "Passwort gespeichert.");
    }

    @Test
    void testKeineEingabeLeererString() {

        String password1 = "";
        String password2 = "";

        boolean isValid = passwortValidierer.passwordValidation(password1, password2);

        assertFalse(isValid, "Falsche Eingabe des Passwortes.");
    }

    @Test
    void testPasswortIstSehrLangFunktioniertTrotzdem() {

        String password1 = "Pässwort12345678901234567890123456789012345678901234567890!";
        String password2 = "Pässwort12345678901234567890123456789012345678901234567890!";

        boolean isValid = passwortValidierer.passwordValidation(password1, password2);

        assertTrue(isValid, "Passwort gespeichert.");
    }

    @Test
    void testPasswoerterSindNull() {
        String password1 = null;
        String password2 = null;

        boolean isValid = passwortValidierer.passwordValidation(password1, password2);

        assertFalse(isValid,
                "Passwörter dürfen nicht null sein.");
    }
    @Test
    void testPasswort1NullUndPasswort2Leer() {
        String password1 = null;
        String password2 = "";

        boolean isValid = passwortValidierer.passwordValidation(password1, password2);

        assertFalse(isValid, "Passwort darf weder null noch leer sein.");
    }

    @Test
    void testPasswort2NullUndPasswort2Leer() {
        String password1 = "";
        String password2 = null;

        boolean isValid = passwortValidierer.passwordValidation(password1, password2);

        assertFalse(isValid, "Passwort darf weder null noch leer sein.");
    }
    @Test
    void testPassword1IstLeer() {
        String password1 = "";
        String password2 = "Passw0rd!";

        boolean isValid = passwortValidierer.passwordValidation(password1, password2);

        assertFalse(isValid, "Passwort1 ist leer und sollte invalide sein.");
    }
    @Test
    void testPassword2IstLeer() {
        String password1 = "Passw0rd!";
        String password2 = "";

        boolean isValid = passwortValidierer.passwordValidation(password1, password2);

        assertFalse(isValid, "Passwort2 ist leer und sollte invalide sein.");
    }


    @Test
    void testPasswortNurSonderzeichen() {
        String password1 = "!§$%&/()=?";
        String password2 = "!§$%&/()=?";

        boolean isValid = passwortValidierer.passwordValidation(password1, password2);

        assertFalse(isValid, "Passwort darf nicht nur aus Sonderzeichen bestehen.");
    }
}


