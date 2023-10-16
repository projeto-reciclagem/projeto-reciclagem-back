package com.projeto.sprint.projetosprint.domain.repository;

import com.projeto.sprint.projetosprint.domain.cooperativa.Cooperativa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CooperativaRepository extends JpaRepository<Cooperativa, Integer> {
    Boolean existsByEmail(String email);
}
