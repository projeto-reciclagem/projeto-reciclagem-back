package com.projeto.sprint.projetosprint.domain.entity.material;

import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String nome;

    private Double precoPorKilo;

    @ManyToOne
    @JoinColumn(name = "fk_cooperativa")
    private Cooperativa cooperativa;
}
