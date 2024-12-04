package com.example.application.services.registrierung;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PasswortValidierer {

    public boolean passwordValidation(String enteredPassword1, String enteredPassword2) {
        // Exceptions sollten vermieden werden
        if (enteredPassword1 == null || enteredPassword2 == null || enteredPassword1.isEmpty() || enteredPassword2.isEmpty()) {
            return false;
        }

        // Exceptions sollten vermieden werden
        if (!Objects.equals(enteredPassword1, enteredPassword2)) {
            return false;
        }

        return enteredPassword1.length() >= 8 && // Mindestlänge 8 Zeichen
               containsSpecialCharacter(enteredPassword1) && // Mindestens ein Sonderzeichen
               containsNumbers(enteredPassword1) && // Mindestens eine Zahl
               containsLetters(enteredPassword1); // Mindestens ein Buchstabe
    }

    private boolean containsSpecialCharacter(String text) {
        return text.matches(".*[!§$%&/()=?].*");
    }

    private boolean containsNumbers(String text) {
        return text.matches(".*[0-9].*");
    }

    private boolean containsLetters(String text) {
        return text.matches(".*[a-zA-ZäÄöÖüÜ].*");
    }
}
