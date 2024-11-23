package com.example.application.models;

import com.example.application.models.benachrichtigung.Benachrichtigung;
import com.example.application.models.benachrichtigung.LeseStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table
public class Praktikumsbeauftragter {

    @Id
    private String nachname;

    private String vorname;
    private String passwort;

    @Transient
    private List<Benachrichtigung> benachrichtigungList;

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
