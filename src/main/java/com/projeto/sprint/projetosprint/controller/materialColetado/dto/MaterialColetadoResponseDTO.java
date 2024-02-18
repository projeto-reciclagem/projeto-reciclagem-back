package com.projeto.sprint.projetosprint.controller.materialColetado.dto;

import com.projeto.sprint.projetosprint.controller.agendamento.dto.AgendaResponseDTO;
import lombok.Data;

@Data
public class MaterialColetadoResponseDTO {
    private Integer id;
    private String nome;
    private Double qntKgColetado;
    private Double vlrTotalColedo;
    private AgendaResponseDTO agendamento;
}
