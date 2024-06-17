package com.projeto.sprint.projetosprint.controller.agendamento.dto;

import com.projeto.sprint.projetosprint.controller.condominio.dto.CondominioResponseDTO;
import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Status;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendaResponseDTO {
    private Integer id;
    private String cooperativa;
    private String condominio;
    private Integer qtBag;
    private LocalDateTime datAgendamento;
    private Status status;
    private String endereco;
}
