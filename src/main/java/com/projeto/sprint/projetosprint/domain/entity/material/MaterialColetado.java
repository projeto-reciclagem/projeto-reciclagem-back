package com.projeto.sprint.projetosprint.domain.entity.material;

import com.projeto.sprint.projetosprint.domain.entity.agenda.Agenda;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MaterialColetado {
    @Id
    @ManyToOne
    @JoinColumn(name = "fk_material")
    private Material material;

    @Id
    @ManyToOne
    @JoinColumn(name = "fk_agenda")
    private Agenda agenda;

    @NotNull
    private Double pesagemColetada;

    @NotNull
    private Double valorTotal;
}
