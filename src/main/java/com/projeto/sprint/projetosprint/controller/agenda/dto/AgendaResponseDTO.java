package com.projeto.sprint.projetosprint.controller.agenda.dto;

import com.projeto.sprint.projetosprint.controller.condominio.dto.CondominioResponseDTO;
import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Status;
import com.projeto.sprint.projetosprint.domain.entity.condominio.Condominio;
import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendaResponseDTO {
    private Integer id;
    @FutureOrPresent
    private LocalDateTime datAgendamento;
    @FutureOrPresent
    private LocalDateTime datRetirada;

    @Positive
    private Integer qtBag;
    private Status status;

    private CooperativaResponseDTO cooperativa;
    private CondominioResponseDTO condominio;
}
