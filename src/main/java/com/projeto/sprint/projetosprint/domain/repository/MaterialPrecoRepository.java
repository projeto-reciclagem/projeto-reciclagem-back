package com.projeto.sprint.projetosprint.domain.repository;

import com.projeto.sprint.projetosprint.domain.entity.material.MaterialPreco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialPrecoRepository extends JpaRepository<MaterialPreco, Integer> {
    List<MaterialPreco> findByCooperativaId(int id);
}
