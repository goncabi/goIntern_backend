package com.example.application.repositories;

import com.example.application.models.Praktikumsantrag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PraktikumsantragRepository extends JpaRepository<Praktikumsantrag, Long> {
}
