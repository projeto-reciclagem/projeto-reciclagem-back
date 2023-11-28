package com.projeto.sprint.projetosprint.domain.repository;

import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CooperativaRepository extends JpaRepository<Cooperativa, Integer> {

    Cooperativa findByUsuarioId(Long id);
}
