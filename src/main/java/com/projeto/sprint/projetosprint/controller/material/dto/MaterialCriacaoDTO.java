package com.projeto.sprint.projetosprint.controller.material.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MaterialCriacaoDTO {
    private String nome;

    private Double precoPorKilo;
}
