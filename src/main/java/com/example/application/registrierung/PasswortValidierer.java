package com.example.application.registrierung;

import org.springframework.stereotype.Service;

@Service
public class PasswortValidierer {

    public boolean test(String enteredPassword){
        return enteredPassword.length() >= 8 &&
                containsSpecialCharacter(enteredPassword) &&
                containsNumbers(enteredPassword) &&
                containsLetters(enteredPassword);
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
