package com.projeto.sprint.projetosprint.controller.materialPreco.dto;

import lombok.Data;

@Data
public class MaterialPrecoCadastroDTO {
    private Double vlrMaterial;
    private String nome;
    private Integer fkCooperativa;
}
