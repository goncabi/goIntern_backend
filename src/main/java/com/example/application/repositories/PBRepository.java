package com.example.application.repositories;
import com.example.application.models.Praktikumsbeauftragter;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.application.models.AppUserRole;

import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface PBRepository extends JpaRepository<Praktikumsbeauftragter, Long> {
Optional<Praktikumsbeauftragter> findByUsername(String username);
Optional<Praktikumsbeauftragter> findByRole(AppUserRole appUserRole);

}
