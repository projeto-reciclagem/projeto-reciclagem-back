package com.projeto.sprint.projetosprint.controller.material.dto;

import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class MaterialCriacaoDTO {
    @NotBlank
    @Size(max = 255)
    public String nome;

    @Positive
    @NotNull
    public Double valorKg;

    public Integer fkCooperativa;
}
