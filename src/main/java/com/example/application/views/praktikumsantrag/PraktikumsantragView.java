package com.example.application.views.praktikumsantrag;

import com.example.application.components.phonenumberfield.PhoneNumberField;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Praktikumsantrag")
@Route("")
@Menu(order = 0, icon = "line-awesome/svg/user.svg")
public class PraktikumsantragView extends Composite<VerticalLayout> {

    public PraktikumsantragView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();
        TextField textField = new TextField();
        TextField textField2 = new TextField();
        DatePicker datePicker = new DatePicker();
        TextField textField3 = new TextField();
        TextField textField4 = new TextField();
        TextField textField5 = new TextField();
        EmailField emailField = new EmailField();
        PhoneNumberField phoneNumber = new PhoneNumberField();
        TextField textField6 = new TextField();
        ComboBox comboBox = new ComboBox();
        TextArea textArea = new TextArea();
        Checkbox checkbox = new Checkbox();
        TextArea textArea2 = new TextArea();
        Checkbox checkbox2 = new Checkbox();
        HorizontalLayout layoutRow = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Button buttonSecondary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("min-content");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("1000px");
        h3.setText("Antrag und Bestätigungen zur Durchführung des studienbegleitenden\nPraktikums");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        formLayout2Col.setMaxWidth("2000px");
        textField.setLabel("Vorname");
        textField2.setLabel("Nachname");
        datePicker.setLabel("Geburtsdatum");
        textField3.setLabel("Matrikelnummer");
        textField4.setLabel("Straße");
        textField4.setWidth("min-content");
        textField5.setLabel("PLZ/Ort");
        textField5.setWidth("min-content");
        emailField.setLabel("E-Mail");
        phoneNumber.setLabel("Telefonnummer");
        phoneNumber.setWidth("37px");
        phoneNumber.setHeight("61px");
        textField6.setLabel("Vorschlag für den Praktikumsbetreuer_in an der HTW Berlin");
        layoutColumn2.setAlignSelf(FlexComponent.Alignment.CENTER, textField6);
        textField6.setWidth("100%");
        comboBox.setLabel("Semester");
        comboBox.setWidth("min-content");
        setComboBoxSampleData(comboBox);
        textArea.setLabel("Titel der praxisbegleitenden Lehrveranstaltungen:");
        textArea.setWidth("100%");
        checkbox.setLabel(
                "Ich erkläre, dass ich alle Leistungsnachweise, die im\nStudiengang\nVoraussetzung für die Zulassung zum Praktikum sind, erbracht habe.");
        checkbox.setWidth("100%");
        textArea2.setLabel("Mir fehlen folgende Leistungsnachweise:");
        textArea2.setWidth("100%");
        checkbox2.setLabel("Ein Antrag auf Ausnahmezulassung wird hiermit gestellt.");
        checkbox2.setWidth("100%");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        buttonPrimary.setText("Save");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSecondary.setText("Cancel");
        buttonSecondary.setWidth("min-content");
        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(textField);
        formLayout2Col.add(textField2);
        formLayout2Col.add(datePicker);
        formLayout2Col.add(textField3);
        formLayout2Col.add(textField4);
        formLayout2Col.add(textField5);
        formLayout2Col.add(emailField);
        formLayout2Col.add(phoneNumber);
        layoutColumn2.add(textField6);
        layoutColumn2.add(comboBox);
        layoutColumn2.add(textArea);
        layoutColumn2.add(checkbox);
        layoutColumn2.add(textArea2);
        layoutColumn2.add(checkbox2);
        layoutColumn2.add(layoutRow);
        layoutRow.add(buttonPrimary);
        layoutRow.add(buttonSecondary);
    }

    record SampleItem(String value, String label, Boolean disabled) {
    }

    private void setComboBoxSampleData(ComboBox comboBox) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("first", "First", null));
        sampleItems.add(new SampleItem("second", "Second", null));
        sampleItems.add(new SampleItem("third", "Third", Boolean.TRUE));
        sampleItems.add(new SampleItem("fourth", "Fourth", null));
        comboBox.setItems(sampleItems);
        comboBox.setItemLabelGenerator(item -> ((SampleItem) item).label());
    }
}
