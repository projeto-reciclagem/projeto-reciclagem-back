package com.projeto.sprint.projetosprint.controller.materialColetado.dto;

import lombok.Data;

@Data
public class MaterialColetadoCadastroDTO {
    private Double qntKgColeado;
    private Integer fkAgendamento;
    private Integer fkMaterialPreco;
}
