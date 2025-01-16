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
/**
 * Service für die Generierung von Mock-Daten für Praktikumsanträge.
 * <p>
 * Diese Klasse verwendet die Bibliothek {@link Faker}, um realistische, aber zufällige Daten
 * für Praktikumsanträge zu generieren. Die generierten Daten umfassen persönliche Informationen
 * der Studentinnen, Details zur Praktikumsstelle und relevante Statusinformationen.
 * </p>
 *
 * <h2>Hauptfunktionen</h2>
 * <ul>
 *   <li>Generiert eine definierte Anzahl von zufälligen Praktikumsanträgen.</li>
 *   <li>Speichert die generierten Anträge in der Datenbank.</li>
 *   <li>Unterstützt Daten für Inlands- und Auslandspraktika.</li>
 * </ul>
 *
 * <h2>Verwendung</h2>
 * <p>
 * Diese Klasse wird normalerweise über den Controller aufgerufen, um Testdaten für
 * Entwicklungs- oder Testzwecke zu generieren.
 * </p>
 *
 * <h2>Beispiel</h2>
 * <pre>
 * MockDataService mockDataService = new MockDataService(praktikumsantragRepository);
 * mockDataService.generateMockData(20);
 * </pre>
 *
 * <h2>Details zur Generierung</h2>
 * <ul>
 *   <li>Persönliche Daten: Matrikelnummer, Name, Vorname, Geburtsdatum, Kontaktinformationen.</li>
 *   <li>Praktikumsdetails: Name und Adresse der Praktikumsstelle, Ansprechpartner, Tätigkeiten.</li>
 *   <li>Praktikumstyp: Inland oder Ausland mit entsprechenden Ländern und Bundesländern.</li>
 * </ul>
 *
 * <h2>Abhängigkeiten</h2>
 * <ul>
 *   <li>{@link PraktikumsantragRepository}: Schnittstelle zur Datenbank, um Anträge zu speichern.</li>
 *   <li>{@link Faker}: Bibliothek zur Generierung von zufälligen Daten.</li>
 * </ul>
 *
 * <h2>Autor</h2>
 * <p>Gabriela Goncalvez</p>
 */
@AllArgsConstructor
@Service
public class MockDataService {

    @Autowired
    private PraktikumsantragRepository praktikumsantragRepository;

    /**
     * Generiert eine definierte Anzahl von Praktikumsanträgen mit zufälligen Daten
     * und speichert diese in der Datenbank.
     *
     * @param count Anzahl der zu generierenden Anträge.
     */
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

            antrag.setGebDatumStudentin(faker.date().birthday(20, 40).toInstant()
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
                    "Informatik", "Wirtschaftsinformatik", "Umweltinformatik",
                    "Angwandte Informatik", "Medieninformatik"
            ));

            boolean auslandspraktikum = faker.bool().bool();
            if (auslandspraktikum) {
                // Für Ausland: Land und ein generisches Bundesland
                antrag.setLandPraktikumsstelle(faker.country().name());
                antrag.setBundeslandPraktikumsstelle("Keine Angaben notwendig");
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
