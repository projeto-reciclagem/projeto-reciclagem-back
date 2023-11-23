package com.projeto.sprint.projetosprint.controller.agenda;

import com.projeto.sprint.projetosprint.controller.agenda.dto.AgendaCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.agenda.dto.AgendaResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Agenda;

public class AgendaMapper {
    public static AgendaResponseDTO of(Agenda agenda) {
        AgendaResponseDTO dto = new AgendaResponseDTO();

        dto.setId(agenda.getId());
        dto.setDatAgendamento(agenda.getDatAgendamento());
        dto.setDatRetirada(agenda.getDatRetirada());
        dto.setQtBag(agenda.getQtBag());
        dto.setStatus(agenda.getStatus());
        dto.setCooperativa(agenda.getCooperativa());
        dto.setCondominio(agenda.getCondominio());
        return dto;
    }

    public static Agenda of(AgendaCriacaoDTO agendaDTO){
        return null;
    }
}
