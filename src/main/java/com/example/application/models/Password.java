package com.example.application.models;

import java.util.Scanner;

public class Password {

    private String password;


    public String setPassword() {

        System.out.println("Bitte wähle ein Passwort für deinen Login-Bereich: ");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String enteredPassword = scanner.nextLine();

            if (isPasswordValid(enteredPassword)) {
                this.password = enteredPassword;
                System.out.println("Passwort wurde erfolgreich gesetzt.");
                return enteredPassword;
            } else {
                System.out.println("Passwort muss mindestens 8 Zeichen lang sein und soll aus Buchstaben, Zahlen und Sonderzeichenen (! § $ % & / ( ) = ? ) bestehen.");
                System.out.println("Bitte versuche es erneut:");
            }

            return enteredPassword;
        }

    }

    private boolean isPasswordValid(String enteredPassword) {
        return enteredPassword.length() >= 8 &&  containsSpecialCharacter(enteredPassword) &&
                containsANumber(enteredPassword) &&
                containsLetters(enteredPassword);
    }

    private boolean containsSpecialCharacter(String text) {
        return text.matches(".*[!§$%&/()=?].*");
    }

    private boolean containsANumber(String text) {
        return text.matches(".*[0-9].*");
    }

    private boolean containsLetters(String text) {
        return text.matches(".*[a-zA-ZäÄöÖüÜ].*");
    }



    public boolean enterPassword() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Bitte Passwort eingeben: ");
            String enteredPassword = scanner.nextLine();

            if (this.password.equals(enteredPassword)) {
                System.out.println("Passwort korrekt");
                scanner.close();
                return true;
            }
            else {
                System.out.println("Eingegebenes Passwort ist falsch. Bitte erneut versuchen.");
            }
        }
    }

    public static void main(String[] args) {

        Password password = new Password();
        password.setPassword();

    }

}


