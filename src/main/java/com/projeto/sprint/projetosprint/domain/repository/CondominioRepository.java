package com.projeto.sprint.projetosprint.domain.repository;

import com.projeto.sprint.projetosprint.domain.entity.condominio.Condominio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CondominioRepository extends JpaRepository<Condominio, Integer> {
    Condominio findByUsuarioId(Long id);

    Optional<Condominio> findByUsuarioEmail(String email);


}
