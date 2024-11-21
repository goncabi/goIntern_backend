package com.example.application.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
public class Benachrichtigung {

    private String matrikelnummer; //Empfänger
    private String nachricht;
    private Date datum;
}
