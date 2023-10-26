package com.projeto.sprint.projetosprint.domain.repository;

import com.projeto.sprint.projetosprint.domain.condominio.Condominio;
import com.projeto.sprint.projetosprint.domain.cooperativa.Cooperativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface CondominioRepository extends JpaRepository<Condominio, Integer> {
    Boolean existsByEmail(String email);
}
