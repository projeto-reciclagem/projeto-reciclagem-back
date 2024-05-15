package com.projeto.sprint.projetosprint.domain.repository;

import com.projeto.sprint.projetosprint.domain.entity.agenda.Agenda;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("SELECT a FROM Agenda a WHERE a.cooperativa.id = :id" +
            " AND a.condominio.nome LIKE CONCAT('%',:nomeCliente,'%')" +
            " OR (:status IS NULL AND a.status = :status)")
    List<Agenda>findByCooperativa(int id, String nomeCliente, Status status);

    @Query("SELECT a FROM Agenda a WHERE a.condominio.id = :id" +
            " AND a.condominio.nome LIKE CONCAT('%',:nomeCliente,'%')" +
            " AND (:status IS NULL AND a.status = :status)")
    List<Agenda>findByCondominio(int id, String nomeCliente, Status status);

    @Query("SELECT COUNT(a) FROM Agenda a WHERE a.condominio.id = :idCondominio AND" +
            " a.datAgendamento BETWEEN :endData AND :data")
    Integer countByCondominioId(int idCondominio, LocalDateTime endData, LocalDateTime data);

    @Query("SELECT COUNT(a) FROM Agenda a WHERE a.cooperativa.id = :idCooperativa AND " +
            "a.datAgendamento BETWEEN :endData AND :data")
    Integer countByCooperativaId(int idCooperativa, LocalDateTime endData, LocalDateTime data);

    @Query("SELECT a FROM Agenda a WHERE a.condominio.id = :idCondominio AND a.status = :status " +
            "ORDER BY a.datRetirada DESC " +
            "LIMIT 1")
    Optional<Agenda>ultimaColetaFeita(int idCondominio, Status status);

    @Query("SELECT COUNT(1) FROM Agenda a WHERE a.cooperativa.id = :idCooperativa AND" +
            " a.datAgendamento BETWEEN :data AND :endData " +
            " AND a.status = 1")
    Integer realizadoMes(int idCooperativa, LocalDateTime data,LocalDateTime endData);

    @Query("SELECT COUNT(1) FROM Agenda a WHERE a.cooperativa.id = :idCooperativa AND" +
            " a.datAgendamento BETWEEN :data AND :endData " +
            " AND a.status = 2")
    Integer canceladoMes(int idCooperativa, LocalDateTime data,LocalDateTime endData);

    @Query("SELECT a FROM Agenda a JOIN Usuario u ON a.condominio.usuario.id = u.id " +
            "WHERE a.cooperativa.id = :cooperativaId " +
            "AND (:status IS NULL OR a.status = :status) " +
            "AND (:nomeCliente IS NULL OR a.condominio.nome ILIKE %:nomeCliente%)")
    List<Agenda> buscarPorCooperativaComFiltros(@Param("cooperativaId") int cooperativaId,
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
}
