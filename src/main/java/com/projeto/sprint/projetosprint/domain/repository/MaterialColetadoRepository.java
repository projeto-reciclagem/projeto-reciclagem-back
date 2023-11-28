package com.projeto.sprint.projetosprint.domain.repository;

import com.projeto.sprint.projetosprint.domain.entity.material.MaterialColetado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialColetadoRepository extends JpaRepository<MaterialColetado, Integer> {
    MaterialColetado findByAgendaCooperativaId(int id);
    MaterialColetado findByAgendaCondominioId(int id);
    MaterialColetado findByAgendaId(int id);
}
