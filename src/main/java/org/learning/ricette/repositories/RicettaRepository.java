package org.learning.ricette.repositories;

import org.learning.ricette.model.Ricetta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RicettaRepository extends JpaRepository<Ricetta, Integer> {
}
