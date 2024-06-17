package com.projeto.sprint.projetosprint.controller.material.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PesagemTotalColetadaDTO {
    private Double pesagemAtual;
    private Double pesagemAnterior;
    private Double valorDiferenca;
}
