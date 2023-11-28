package com.projeto.sprint.projetosprint.domain.entity.material;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialUltimaSemana {
    private String nome;
    private Double qntKgColetada;
}
