package com.projeto.sprint.projetosprint.controller.materialColetado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ColetaDiariaDTO {
    private LocalDate dataColeta;
    private Integer qntTotal;
}
