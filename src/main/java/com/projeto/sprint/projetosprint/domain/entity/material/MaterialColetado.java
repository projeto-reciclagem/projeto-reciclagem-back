package com.projeto.sprint.projetosprint.domain.entity.material;

import com.projeto.sprint.projetosprint.domain.entity.agenda.Agenda;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MaterialColetado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double qntKgColeado;

    private Double vlrTotalColedo;

    @ManyToOne
    @JoinColumn(name = "fk_materialPreco")
    private MaterialPreco materialPreco;

    @ManyToOne
    @JoinColumn(name = "fk_agenda")
    private Agenda agenda;
}
