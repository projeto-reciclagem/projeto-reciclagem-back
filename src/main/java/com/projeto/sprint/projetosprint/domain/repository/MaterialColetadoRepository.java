package com.projeto.sprint.projetosprint.domain.repository;

import com.projeto.sprint.projetosprint.domain.entity.material.MaterialColetado;
import com.projeto.sprint.projetosprint.domain.entity.material.MaterialUltimaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MaterialColetadoRepository extends JpaRepository<MaterialColetado, Integer> {
    List<MaterialColetado> findByAgendaCooperativaId(int id);
    MaterialColetado findByAgendaCondominioId(int id);
    MaterialColetado findByAgendaId(int id);

    @Query("SELECT SUM(mc.qntKgColetado) FROM MaterialColetado mc WHERE mc.agenda.cooperativa.id = :idCooperativa AND" +
            " mc.agenda.datAgendamento BETWEEN :endData AND :data")
    Double totalColetadoUltimaSemana(int idCooperativa, LocalDateTime endData, LocalDateTime data);

    @Query("SELECT mc.materialPreco.nome, SUM(mc.qntKgColetado) as qntKgColetado from MaterialColetado mc " +
            "WHERE mc.agenda.cooperativa.id = :idCooperativa AND mc.agenda.datAgendamento BETWEEN :endData AND :data " +
            "GROUP BY mc.materialPreco.nome "+
            "ORDER BY qntKgColetado LIMIT 1")
    String buscarMaterialMaisReciclado(int idCooperativa, LocalDateTime endData, LocalDateTime data);

    @Query("SELECT mc from MaterialColetado mc " +
            "WHERE mc.agenda.cooperativa.id = :idCooperativa AND mc.agenda.datAgendamento BETWEEN :endData AND :data ")
    List<MaterialColetado> reciclagemSemanal(int idCooperativa, LocalDateTime endData, LocalDateTime data);

    @Query("SELECT SUM(mc.qntKgColetado) FROM MaterialColetado mc WHERE mc.agenda.cooperativa.id = :idCooperativa")
    Double quantidadeKgTotal(int idCooperativa);

    @Query("SELECT SUM(mc.qntKgColetado) FROM MaterialColetado mc WHERE mc.agenda.condominio.id = :idCondominio AND" +
            " mc.agenda.datAgendamento BETWEEN :endData AND :data")
    Double valorTotalUltimoMes(int idCondominio, LocalDateTime endData, LocalDateTime data);
}
