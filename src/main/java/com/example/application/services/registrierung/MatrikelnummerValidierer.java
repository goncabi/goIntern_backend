package com.example.application.services.registrierung;

import org.springframework.stereotype.Service;

@Service
public class MatrikelnummerValidierer{

    public boolean isMatrikelnummerValid(String matrikelnummer) {
        String firstSignMatrikelnummer = matrikelnummer.substring(0, 2);
        return matrikelnummer.length() == 8 && (firstSignMatrikelnummer.equals("s0"));
    }

}
