package com.projeto.sprint.projetosprint.controller.materialPreco.dto;

import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;
import lombok.Data;

@Data
public class MaterialPrecoResponseDTO {
    private Integer id;
    private String nome;
    private Double vlrMaterial;
}
