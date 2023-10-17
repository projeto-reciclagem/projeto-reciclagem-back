package com.projeto.sprint.projetosprint.repository;

import com.projeto.sprint.projetosprint.entity.AgendaColeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<AgendaColeta, Integer> {
}
