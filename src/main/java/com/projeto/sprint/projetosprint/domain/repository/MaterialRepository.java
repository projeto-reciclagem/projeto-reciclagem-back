package com.projeto.sprint.projetosprint.domain.repository;

import com.projeto.sprint.projetosprint.domain.entity.material.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
}
