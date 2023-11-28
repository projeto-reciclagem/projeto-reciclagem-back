package com.projeto.sprint.projetosprint.domain.repository;

import com.projeto.sprint.projetosprint.domain.entity.material.MaterialColetado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MaterialColetadoRepository extends JpaRepository<MaterialColetado, Integer> {
    MaterialColetado findByAgendaCooperativaId(int id);
    MaterialColetado findByAgendaCondominioId(int id);
    MaterialColetado findByAgendaId(int id);

    @Query("SELECT SUM(mc.qntKgColetado) FROM MaterialColetado mc WHERE mc.agenda.cooperativa.id = :idCooperativa AND" +
            " mc.agenda.datAgendamento BETWEEN :endData AND :data")
    Double totalColetadoUltimaSemana(int idCooperativa, LocalDateTime endData, LocalDateTime data);
}
