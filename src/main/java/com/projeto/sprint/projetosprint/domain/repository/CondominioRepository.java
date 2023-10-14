package com.projeto.sprint.projetosprint.domain.repository;

import com.projeto.sprint.projetosprint.domain.condominio.Condominio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CondominioRepository
        extends JpaRepository<Condominio, Integer> {

    List<Condominio> findByCnpj(String cnpj);
    Optional<Condominio> findByEmail(String email);
}
