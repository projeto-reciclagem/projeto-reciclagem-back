package com.projeto.sprint.projetosprint.controller.agendamento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgendaRealizadasMesDTO {
    private Integer qntMesAtual;
    private Integer qntMesAnterior;
    private Integer vlrPorcentagemDiferenca;
}
