package com.example.application.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Nachricht {
    @Id
    @GeneratedValue
    private Long nachrichtID;

    @ManyToOne
    @JoinColumn
    private Studentin studentin; //Empfänger

    private String nachricht;
}
