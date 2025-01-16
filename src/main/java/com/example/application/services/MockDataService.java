package com.example.application.services;

import com.example.application.models.Praktikumsantrag;
import com.example.application.models.StatusAntrag;
import com.example.application.repositories.PraktikumsantragRepository;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;

@AllArgsConstructor
@Service
public class MockDataService {

    @Autowired
    private PraktikumsantragRepository praktikumsantragRepository;


    public void generateMockData(int count) {
        // Faker inisialisieren mit deutschen daten
        Faker faker = new Faker(new Locale("de"));

        // 20 Anträge erstellen
        for (int i = 1; i <= count; i++) {
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

            // Vorschlag für Praktikumsbetreuer*in
            antrag.setVorschlagPraktikumsbetreuerIn(faker.name().fullName()); // Generar un nombre ficticio

            // Praktikumssemester (SoSe / WiSe)
            antrag.setPraktikumssemester(faker.options().option("SoSe", "WiSe"));

            // Studiensemester (Entre 1 y 10, por ejemplo)
            antrag.setStudiensemester(faker.number().numberBetween(1, 10));

            // Studiengang (Programas de estudio ficticios)
            antrag.setStudiengang(faker.options().option(
                    "Informatik", "Wirtschaftsinformatik", "Maschinenbau",
                    "Elektrotechnik", "Medieninformatik"
            ));

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

            praktikumsantragRepository.save(antrag);
        }
    }

}
