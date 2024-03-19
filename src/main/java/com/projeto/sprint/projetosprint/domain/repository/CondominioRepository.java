package com.projeto.sprint.projetosprint.domain.repository;

import com.projeto.sprint.projetosprint.domain.entity.condominio.Condominio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CondominioRepository extends JpaRepository<Condominio, Integer> {
    Condominio findByUsuarioId(Long id);


}
