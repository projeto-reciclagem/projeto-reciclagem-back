package com.projeto.sprint.projetosprint.controller.materialColetado.dto;

import com.projeto.sprint.projetosprint.controller.agenda.dto.AgendaResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Agenda;
import lombok.Data;

@Data
public class MaterialColetadoResponseDTO {
    private Integer id;
    private String nome;
    private Double qntKgColeado;
    private Double vlrTotalColedo;
    private AgendaResponseDTO agendamento;
}
