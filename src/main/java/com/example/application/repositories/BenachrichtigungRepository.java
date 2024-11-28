package com.example.application.repositories;

import com.example.application.models.benachrichtigung.Benachrichtigung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BenachrichtigungRepository extends JpaRepository<Benachrichtigung, Long> {
    List<Benachrichtigung> findByEmpfaenger_inOrderByDatumAsc(String empfaenger_in);
}
