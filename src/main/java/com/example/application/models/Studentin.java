package com.example.application.models;

import com.example.application.models.benachrichtigung.Benachrichtigung;
import com.example.application.models.benachrichtigung.LeseStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Data
@Entity
@Table(name = "studentin")
public class Studentin {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matrikelnummer", unique = true, nullable = false)
    private String matrikelnummer;
    //private List<Benachrichtigung> nachrichtenliste;
    private String password;
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
    @Transient
    private List<Benachrichtigung> benachrichtigungList;



    public Studentin() {
    }




    public Studentin(String matrikelnummer, String password, AppUserRole appUserRole) {
        benachrichtigungList = new ArrayList<>();
        this.matrikelnummer = matrikelnummer;
        this.password = password;
        this.appUserRole = appUserRole;
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

}

