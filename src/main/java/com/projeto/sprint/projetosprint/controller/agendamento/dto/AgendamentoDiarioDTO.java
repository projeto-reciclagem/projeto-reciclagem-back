package com.projeto.sprint.projetosprint.controller.agendamento.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgendamentoDiarioDTO {
  private LocalDate dataColeta;
  private Integer coletados;
}
