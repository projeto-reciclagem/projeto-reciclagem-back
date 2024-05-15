package com.projeto.sprint.projetosprint.controller.agendamento.dto;

import com.projeto.sprint.projetosprint.controller.condominio.dto.CondominioResponseDTO;
import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendaListagemDTO {
    private Integer id;
    private LocalDateTime datAgendamento;
    private LocalDateTime datRetirada;
    private Integer qtdBags;
    private Status status;

    private CooperativaResponseDTO cooperativa;
    private CondominioResponseDTO condominio;
}
