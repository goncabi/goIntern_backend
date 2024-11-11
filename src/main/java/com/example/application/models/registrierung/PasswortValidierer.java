package com.example.application.models.registrierung;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PasswortValidierer {

    public boolean passwordValidation(String enteredPassword1, String enteredPassword2) {
        if (Objects.equals(enteredPassword1, enteredPassword2)) {
            return enteredPassword1.length() >= 8 &&
                    containsSpecialCharacter(enteredPassword1) &&
                    containsNumbers(enteredPassword1) &&
                    containsLetters(enteredPassword1);
        }
        return false;

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
