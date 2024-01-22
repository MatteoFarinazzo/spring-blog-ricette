package org.learning.ricette.repositories;

import org.learning.ricette.model.Ricetta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RicettaRepository extends JpaRepository<Ricetta, Integer> {

    List<Ricetta> findByTitleContaining(String search);
}
