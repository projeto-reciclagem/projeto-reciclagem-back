package com.projeto.sprint.projetosprint.controller.agendamento.dto;

import lombok.Data;

import java.util.List;

@Data
public class AgendaListagemResponseDTO {
    private List<AgendaListagemDTO> schedules;
    private Meta meta;

    @Data
    public static class Meta {
        private int pageIndex;
        private int perPage;
        private long totalCount;
    }
}
