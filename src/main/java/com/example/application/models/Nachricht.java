package com.example.application.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "nachricht")
public class Nachricht {
    @Id
    @GeneratedValue
    private Long nachrichtid;
    private String nachricht;

    @ManyToOne
    @JoinColumn
    private Studentin studentin;

}
