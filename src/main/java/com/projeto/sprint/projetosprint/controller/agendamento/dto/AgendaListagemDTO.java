package com.projeto.sprint.projetosprint.controller.agendamento.dto;

import com.projeto.sprint.projetosprint.controller.condominio.dto.CondominioSimpleResponseDTO;
import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaSimpleResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendaListagemDTO {
    private Integer id;
    private LocalDateTime datAgendamento;
    private LocalDateTime datRetirada;
    private Integer qtdBags;
    private Status status;

    private CooperativaSimpleResponseDTO cooperativa;
    private CondominioSimpleResponseDTO condominio;
}
