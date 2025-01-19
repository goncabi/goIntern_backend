package com.example.application.repositories;

import com.example.application.models.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PosterRepository extends JpaRepository<Poster, Long> {
    Optional<Poster> findByMatrikelnummer(String matrikelnummer);
}
