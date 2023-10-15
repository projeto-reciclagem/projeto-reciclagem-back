package com.projeto.sprint.projetosprint.repository;

import com.projeto.sprint.projetosprint.entity.Cooperativa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CooperativaRepository
        extends JpaRepository<Cooperativa, Integer> {

    Integer countByEmailContainsIgnoreCase(String email);


}
