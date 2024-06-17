package com.projeto.sprint.projetosprint.domain.repository;

import com.projeto.sprint.projetosprint.domain.entity.material.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
    List<Material> findAllByCooperativaId(Integer cooperativaId);

    @Query("SELECT m.nome FROM MaterialColetado mc JOIN Agenda a ON mc.agenda.id = a.id" +
            " JOIN Material m on mc.material.id = m.id" +
            " WHERE a.datRetirada BETWEEN :initialDate AND :finalDate AND a.status = 2 " +
            "ORDER BY mc.pesagemColetada DESC LIMIT 1")
    String findMostCollectedMaterialMonth(LocalDateTime initialDate, LocalDateTime finalDate);

    @Query("SELECT COALESCE(SUM(mc.pesagemColetada), 0) FROM MaterialColetado mc " +
            "JOIN Agenda a ON mc.agenda.id = a.id " +
            "WHERE a.datRetirada BETWEEN :initialDate AND :finalDate AND a.cooperativa.id = :id")
    Double getTotalWeightColleted(Integer id, LocalDateTime initialDate, LocalDateTime finalDate);
}
