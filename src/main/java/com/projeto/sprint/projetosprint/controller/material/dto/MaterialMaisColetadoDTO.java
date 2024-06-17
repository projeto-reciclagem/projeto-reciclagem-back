package com.projeto.sprint.projetosprint.controller.material.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class MaterialMaisColetadoDTO {
    private String materialAtual;
    private String materialAnterior;
}
