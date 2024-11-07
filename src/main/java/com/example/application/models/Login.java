package com.example.application.models;

import java.util.Scanner;

public class Login {

    private String password;

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

        Login login = new Login();
        login.enterPassword();

    }

}


