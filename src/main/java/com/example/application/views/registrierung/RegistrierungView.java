package com.example.application.views.registrierung;

import com.example.application.services.registrierung.RegistrierungService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Route("registrieren")
public class RegistrierungView extends Composite {

    private final RegistrierungService registrierungService;

    @Override
    protected Component initContent() {
        TextField matrikelnummer = new TextField("Matrikelnummer");
        PasswordField passwort1 = new PasswordField("Passwort");
        PasswordField passwort2 = new PasswordField("Passwort bestätigen");
        return new VerticalLayout(
                new H1("Registrierung"),
                matrikelnummer,
                passwort1,
                passwort2,
                new Button("Registrieren", event -> register(
                        matrikelnummer.getValue(),
                        passwort1.getValue(),
                        passwort2.getValue()
                ))
        );
    }

    private void register(String matrikelnummer, String passwort1, String passwort2) {
        if(matrikelnummer.trim().isEmpty()) {
            Notification.show("Bitte geben Sie ihre Matrikelnummer ein.");
        }
        if(passwort1.trim().isEmpty()) {
            Notification.show("Bitte geben Sie ein von Ihnen selbstgewähltes Passwort ein.");
        }
        else{
            registrierungService.registrieren(matrikelnummer, passwort1, passwort2);
            Notification.show("Registrierung war erfolgreich.");
        }
    }
}
