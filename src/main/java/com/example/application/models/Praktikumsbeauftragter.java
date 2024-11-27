package com.example.application.models;

import com.example.application.models.benachrichtigung.Benachrichtigung;
import com.example.application.models.benachrichtigung.LeseStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Data
@Entity
@Table(name = "praktikumsbeauftragter")

public class Praktikumsbeauftragter {


    @Id
    private String username;
    private String passwort;
    @Enumerated(EnumType.STRING)
    private AppUserRole app_user_role;
    @Transient
    private List<Benachrichtigung> benachrichtigungList = new ArrayList<>();
    private String studiengang;

    public Praktikumsbeauftragter() {
    }

    public Praktikumsbeauftragter(String username, String passwort, AppUserRole appUserRole, String studiengang) {
            this.username = username;
            this.passwort = passwort;
            this.app_user_role = appUserRole;
            this.studiengang = studiengang;

        }


    public void addNachricht(Benachrichtigung benachrichtigung) {
        this.benachrichtigungList.add(benachrichtigung);
    }

    public void removeNachricht(Benachrichtigung benachrichtigung) {
        this.benachrichtigungList.remove(benachrichtigung);
    }

    public List<Benachrichtigung> readUngeleseneNachrichten(){
        List<Benachrichtigung> ungeleseneNachrichten = new ArrayList<>();
        for(int index = 0 ; index < benachrichtigungList.size() ; index++){
            if (benachrichtigungList.get(index).getLeseStatus() == LeseStatus.UNGELESEN) {
                ungeleseneNachrichten.add(benachrichtigungList.get(index));
            }
        }
        return ungeleseneNachrichten;
    }

    public boolean existierenUngeleseneNachrichten(){
        //Zur Überprüfung, ob Glocke leuchtet oder nicht
        for(int index = 0 ; index < benachrichtigungList.size() ; index++){
            if (benachrichtigungList.get(index).getLeseStatus() == LeseStatus.UNGELESEN) {
                return true;
            }
        }
        return false;
    }
}
