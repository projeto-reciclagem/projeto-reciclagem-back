package com.projeto.sprint.projetosprint.domain.entity.material;

import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MaterialPreco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double vlrMaterial;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "fk_cooperativa")
    private Cooperativa cooperativa;
}
