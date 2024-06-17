package com.projeto.sprint.projetosprint.domain.repository;

import com.projeto.sprint.projetosprint.domain.entity.agenda.Agenda;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Integer> {
    @Query("SELECT COUNT(1) FROM Agenda a JOIN cooperativa c ON a.cooperativa.id = c.id " +
            "JOIN Usuario u ON c.usuario.id = u.id " +
            "WHERE u.email = :email AND a.datAgendamento BETWEEN :data AND :endData AND a.status = 2")
    Integer findAgendamentosRealizados(String email, LocalDateTime data, LocalDateTime endData);

    @Query("SELECT COUNT(1) FROM Agenda a JOIN cooperativa c ON a.cooperativa.id = c.id " +
            "JOIN Usuario u ON c.usuario.id = u.id " +
            "WHERE u.email = :email AND a.datAgendamento BETWEEN :data AND :endData AND a.status = 3")
    Integer findAgendamentosCancelados(String email, LocalDateTime data,LocalDateTime endData);

    @Query("SELECT a FROM Agenda a JOIN Usuario u ON a.condominio.usuario.id = u.id " +
            "WHERE a.cooperativa.id = :cooperativaId " +
            "AND (:status IS NULL OR a.status = :status) " +
            "AND (:nomeCliente IS NULL OR a.condominio.nome ILIKE %:nomeCliente%)")
    List<Agenda> findAgendamentosPorCooperativa(@Param("cooperativaId") int cooperativaId,
                                                @Param("status") Status status,
                                                @Param("nomeCliente") String nomeCliente,
                                                Pageable pageable);

    @Query("SELECT COUNT(a) FROM Agenda a JOIN Usuario u ON a.condominio.usuario.id = u.id " +
            "WHERE a.cooperativa.id = :cooperativaId " +
            "AND (:status IS NULL OR a.status = :status) " +
            "AND (:nomeCliente IS NULL OR a.condominio.nome ILIKE %:nomeCliente%)")
    long contagemDeAgendamentosPorCooperativa(@Param("cooperativaId") int cooperativaId,
                              @Param("status") Status status,
                              @Param("nomeCliente") String nomeCliente);

    @Modifying
    @Transactional
    @Query("UPDATE Agenda a SET a.status = 1 WHERE a.id = :id")
    void approveScheduleId(int id);

    @Modifying
    @Transactional
    @Query("UPDATE Agenda a SET a.status = 2 WHERE a.id = :id")
    void completeScheduleId(int id);

    @Modifying
    @Transactional
    @Query("UPDATE Agenda a SET a.status = 3 WHERE a.id = :id")
    void cancelScheduleId(int id);

    @Query("SELECT COUNT(a.id) FROM Agenda a " +
            " WHERE a.cooperativa.id = :id AND " +
            " a.datRetirada BETWEEN :dtInicial AND :dtFinal AND " +
            " a.status = 2")
    Integer countRealizedSchedulesInPeriod(int id, LocalDateTime dtInicial, LocalDateTime dtFinal);
}
