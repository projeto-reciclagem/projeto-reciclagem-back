package com.projeto.sprint.projetosprint.domain.repository;

import com.projeto.sprint.projetosprint.controller.materialColetado.dto.MaterialColetadoDTO;
import com.projeto.sprint.projetosprint.domain.entity.material.MaterialColetado;
import com.projeto.sprint.projetosprint.domain.entity.material.MaterialUltimaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
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

    @Query("SELECT SUM(mc.qntKgColetado) FROM MaterialColetado mc WHERE mc.agenda.cooperativa.id = :idCooperativa AND " +
            " mc.agenda.datRetirada BETWEEN :dtInicial AND :dtFinal")
    Double quantidadeKgTotal(int idCooperativa, LocalDateTime dtInicial, LocalDateTime dtFinal);

    @Query("SELECT SUM(mc.qntKgColetado) FROM MaterialColetado mc WHERE mc.agenda.condominio.id = :idCondominio AND" +
            " mc.agenda.datAgendamento BETWEEN :endData AND :data")
    Double valorTotalUltimoMes(int idCondominio, LocalDateTime endData, LocalDateTime data);

    @Query("SELECT mc FROM MaterialColetado mc WHERE mc.agenda.condominio.id = :idCondominio AND" +
            " mc.agenda.datAgendamento BETWEEN :endData AND :data")
    List<MaterialColetado> materialPorColetaAno(int idCondominio, LocalDateTime endData, LocalDateTime data);

    @Query("SELECT COUNT(mc.id) FROM MaterialColetado mc WHERE mc.materialPreco.cooperativa.id = :id AND " +
            " mc.agenda.datAgendamento BETWEEN :dtInicial AND :dtFinal")
    Integer materialColetadoMes(int id, LocalDateTime dtInicial, LocalDateTime dtFinal);

    @Query("SELECT SUM(mc.agenda.qtBag) FROM MaterialColetado mc WHERE mc.materialPreco.cooperativa.id = :id AND " +
            " mc.agenda.datAgendamento BETWEEN :dtInicial AND :dtFinal")
    Integer bagsColetadasMes(int id, LocalDateTime dtInicial, LocalDateTime dtFinal);

    @Query("SELECT mc.materialPreco.nome FROM MaterialColetado mc " +
            " WHERE mc.materialPreco.cooperativa.id = :id AND " +
            " mc.agenda.datAgendamento BETWEEN :dtInicial AND :dtFinal" +
            " GROUP BY mc.materialPreco.nome" +
            " ORDER BY SUM(mc.qntKgColetado) DESC LIMIT 1")
    String materiaisMaisColetados(int id, LocalDateTime dtInicial, LocalDateTime dtFinal);

    @Query("SELECT COUNT(mc.agenda.id) FROM MaterialColetado mc " +
            " WHERE mc.materialPreco.cooperativa.id = :id AND " +
            " mc.agenda.datRetirada BETWEEN :dtInicial AND :dtFinal AND " +
            " mc.agenda.status = 1")
    Integer quantidadeColetadoDiario(int id, LocalDateTime dtInicial, LocalDateTime dtFinal);

    @Query("SELECT mc FROM MaterialColetado mc " +
            "WHERE mc.materialPreco.cooperativa.id = :id AND mc.agenda.datAgendamento BETWEEN :dtInicial AND :dtFinal")
    List<MaterialColetado> findByAgendaCooperativaIdMes(int id, LocalDateTime dtInicial, LocalDateTime dtFinal);
}
