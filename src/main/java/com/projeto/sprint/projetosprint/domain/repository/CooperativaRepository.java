package com.projeto.sprint.projetosprint.domain.repository;

import com.projeto.sprint.projetosprint.domain.condominio.Condominio;
import com.projeto.sprint.projetosprint.domain.cooperativa.Cooperativa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CooperativaRepository
        extends JpaRepository<Cooperativa, Integer> {

    List<Cooperativa> findByCnpj(String cnpj);
    Optional<Cooperativa> findByEmail(String email);
}
