package com.example.application.utils;
import com.example.application.models.Praktikumsantrag;
import com.example.application.models.StatusAntrag;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MockDataGenerator {
    public static void main(String[] args) {
        // Faker inisialisieren mit deutschen daten
        Faker faker = new Faker(new Locale("de"));

        // List, um Daten zu speichern
        List<Praktikumsantrag> antraege = new ArrayList<>();


        // 20 Anträge erstellen
        for (int i = 1; i <= 20; i++) {
            // Generiere Daten für den Antrag
            Praktikumsantrag antrag = new Praktikumsantrag();
            antrag.setMatrikelnummer("s0" + faker.number().numberBetween(100000, 999999));
            antrag.setNameStudentin(faker.name().lastName());
            antrag.setVornameStudentin(faker.name().firstName());

            antrag.setGebDatumStudentin(faker.date().birthday(20, 30).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());

            antrag.setStrasseHausnummerStudentin(faker.address().streetAddress());
            antrag.setPlzStudentin(Integer.parseInt(faker.address().zipCode()));
            antrag.setOrtStudentin(faker.address().city());
            antrag.setTelefonnummerStudentin(faker.phoneNumber().phoneNumber());
            antrag.setEmailStudentin(faker.internet().emailAddress());
            antrag.setDatumAntrag(LocalDate.now());
            antrag.setStatusAntrag(StatusAntrag.EINGEREICHT);

            boolean auslandspraktikum = faker.bool().bool();
            if (auslandspraktikum) {
                // Für Ausland: Land und ein generisches Bundesland
                antrag.setLandPraktikumsstelle(faker.country().name());
                antrag.setBundeslandPraktikumsstelle("Keine (Ausland)");
            } else {
                // Für Deutschland: Ein deutsches Bundesland
                antrag.setLandPraktikumsstelle("Deutschland");
                antrag.setBundeslandPraktikumsstelle(faker.options().option(
                        "Bayern", "Berlin", "Hamburg", "Hessen", "Nordrhein-Westfalen", "Sachsen", "Thüringen"
                ));
            }
            antrag.setAuslandspraktikum(auslandspraktikum);



            //Praktikumstelle
            antrag.setNamePraktikumsstelle(faker.company().name());
            antrag.setStrassePraktikumsstelle(faker.address().streetAddress());
            antrag.setPlzPraktikumsstelle(Integer.parseInt(faker.address().zipCode()));
            antrag.setOrtPraktikumsstelle(faker.address().city());
            antrag.setAnsprechpartnerPraktikumsstelle(faker.name().fullName());
            antrag.setTelefonPraktikumsstelle(faker.phoneNumber().phoneNumber());
            antrag.setEmailPraktikumsstelle(faker.internet().emailAddress());
            antrag.setAbteilung("IT");
            antrag.setTaetigkeit("Entwicklung");
            antrag.setStartdatum(LocalDate.now().plusDays(faker.number().numberBetween(1, 30)));
            antrag.setEnddatum(LocalDate.now().plusMonths(3));

            antraege.add(antrag);
        }


        // List wird zum JSON
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new TypeAdapter<LocalDate>() {
                    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

                    @Override
                    public void write(JsonWriter out, LocalDate value) throws IOException {
                        out.value(value.format(formatter));
                    }

                    @Override
                    public LocalDate read(JsonReader in) throws IOException {
                        return LocalDate.parse(in.nextString(), formatter);
                    }
                })
                .create();
        String json = gson.toJson(antraege);


        System.out.println(json);
        // Daten werden in einer Datei gespeichert
        try (FileWriter writer = new FileWriter("mock-data.json")) {
            writer.write(json);
            System.out.println("Mock-Daten gespeichert: mock-data.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



