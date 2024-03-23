package com.projeto.sprint.projetosprint.domain.repository;

import com.projeto.sprint.projetosprint.domain.entity.agenda.Agenda;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AgendaRepository extends JpaRepository<Agenda, Integer> {

    @Query("SELECT a FROM Agenda a WHERE a.cooperativa.id = :idCooperativa AND a.datRetirada BETWEEN :data AND :endData")
    List<Agenda> findByDatRetiradaCooperativa(int idCooperativa, LocalDateTime data, LocalDateTime endData);

    @Query("SELECT a FROM Agenda a WHERE a.condominio.id = :idCondominio AND a.datRetirada BETWEEN :data AND :endData")
    List<Agenda> findByDatRetiradaCondominio(int idCondominio, LocalDateTime data, LocalDateTime endData);


    @Query("SELECT COUNT(a) FROM Agenda a WHERE a.status = 1 AND a.cooperativa.id = :idCooperativa AND" +
            " a.datRetirada BETWEEN :endData AND :data")
    Integer contagemAgendamentoUltimaSemana(int idCooperativa, LocalDateTime endData, LocalDateTime data);

    @Query("SELECT COUNT(a) FROM Agenda a WHERE a.cooperativa.id = :idCooperativa AND" +
            " a.datAgendamento BETWEEN :endData AND :data")
    Integer condominiosAtendidosUltimaSemana(int idCooperativa, LocalDateTime endData, LocalDateTime data);

    List<Agenda>findByCondominioId(int id);
    List<Agenda>findByCooperativaId(int id);

    @Query("SELECT COUNT(a) FROM Agenda a WHERE a.condominio.id = :idCondominio AND" +
            " a.datAgendamento BETWEEN :endData AND :data")
    Integer countByCondominioId(int idCondominio, LocalDateTime endData, LocalDateTime data);

    @Query("SELECT a FROM Agenda a WHERE a.condominio.id = :idCondominio AND a.status = :status " +
            "ORDER BY a.datRetirada DESC " +
            "LIMIT 1")
    Optional<Agenda>ultimaColetaFeita(int idCondominio, Status status);


    @Query("SELECT COUNT(1) FROM Agenda a WHERE a.cooperativa.id = :idCooperativa AND" +
            " a.datAgendamento BETWEEN :data AND :endData " +
            " AND a.status = 3")
    Integer canceladoMes(int idCooperativa, LocalDateTime data,LocalDateTime endData);

}
