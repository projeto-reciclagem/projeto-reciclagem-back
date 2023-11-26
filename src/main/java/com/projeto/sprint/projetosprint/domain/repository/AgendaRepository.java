package com.projeto.sprint.projetosprint.domain.repository;

import com.projeto.sprint.projetosprint.domain.entity.agenda.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Integer> {

    @Query("SELECT a FROM Agenda a WHERE a.datRetirada BETWEEN :data AND :endData")
    List<Agenda> findByDatRetirada(LocalDateTime data, LocalDateTime endData);

    @Query("SELECT COUNT(a) FROM Agenda a WHERE a.status = 1 AND a.cooperativa.id = :idCooperativa AND" +
            " a.datRetirada BETWEEN :endData AND :data")
    Integer contagemAgendamentoUltimaSemana(int idCooperativa, LocalDateTime endData, LocalDateTime data);

    @Query("SELECT COUNT(a) FROM Agenda a WHERE a.cooperativa.id = :idCooperativa AND" +
            " a.datAgendamento BETWEEN :endData AND :data")
    Integer condominiosAtendidosUltimaSemana(int idCooperativa, LocalDateTime endData, LocalDateTime data);
}
