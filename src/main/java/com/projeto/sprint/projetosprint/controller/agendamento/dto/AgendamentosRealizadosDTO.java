package com.projeto.sprint.projetosprint.controller.agendamento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgendamentosRealizadosDTO {
    private Integer qntMesAtual;
    private Integer qntMesAnterior;
    private Double valorDiferenca;
}
