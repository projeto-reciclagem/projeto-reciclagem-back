package com.projeto.sprint.projetosprint.controller.agendamento.dto;

import com.projeto.sprint.projetosprint.domain.entity.agenda.Status;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendaCriacaoDTO {

    private LocalDateTime datRetirada;

    @Positive
    private Integer qtBag;

    private Status status;

    @Positive
    private Integer fkCooperativa;
    @Positive
    private Integer fkCondominio;
}
