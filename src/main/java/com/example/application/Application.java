package com.example.application;

import com.example.application.models.Sicherheitsfrage;
import com.example.application.repositories.SicherheitsfrageRepository;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "praktikumsapp")
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

        Sicherheitsfrage frage1 = new Sicherheitsfrage(1,"Wie lautet der Geburtsname Ihrer Mutter?");
        Sicherheitsfrage frage2 = new Sicherheitsfrage(2,"Wie viele Haustiere hatten Sie, als Sie 10 Jahre alt waren?");
        Sicherheitsfrage frage3 = new Sicherheitsfrage(3,"Wie lautete der Spitzname deines/r besten Freundes/in in der Kindheit?");

    }
}
