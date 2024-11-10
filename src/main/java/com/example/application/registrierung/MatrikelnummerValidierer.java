package com.example.application.registrierung;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class MatrikelnummerValidierer implements Predicate<String> {
    @Override
    public boolean test(String s) {
        //TODO: Regex für die Überprüfung der Matrikelnummer
        return true;
    }
}
