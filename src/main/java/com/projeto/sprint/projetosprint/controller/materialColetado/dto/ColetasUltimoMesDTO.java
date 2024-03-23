package com.projeto.sprint.projetosprint.controller.materialColetado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ColetasUltimoMesDTO {
    private Integer qntMesAtual;
    private Integer qntMesAnterior;
    private Integer vlrPorcentagemDiferenca;
}
