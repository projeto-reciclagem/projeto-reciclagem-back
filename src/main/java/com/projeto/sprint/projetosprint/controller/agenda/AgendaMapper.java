package com.projeto.sprint.projetosprint.controller.agenda;

import com.projeto.sprint.projetosprint.controller.agenda.dto.AgendaCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.agenda.dto.AgendaResponseDTO;
import com.projeto.sprint.projetosprint.controller.condominio.CondominioMapper;
import com.projeto.sprint.projetosprint.controller.cooperativa.CooperativaMapper;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Agenda;
import com.projeto.sprint.projetosprint.domain.entity.condominio.Condominio;
import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;

public class AgendaMapper {
    public static AgendaResponseDTO of(Agenda agenda) {
        AgendaResponseDTO dto = new AgendaResponseDTO();

        dto.setId(agenda.getId());
        dto.setDatAgendamento(agenda.getDatAgendamento());
        dto.setDatRetirada(agenda.getDatRetirada());
        dto.setQtBag(agenda.getQtBag());
        dto.setStatus(agenda.getStatus());

        dto.setCooperativa(CooperativaMapper.of(agenda.getCooperativa()));
        dto.setCondominio(CondominioMapper.of(agenda.getCondominio()));
        return dto;
    }

    public static Agenda of(AgendaCriacaoDTO agendaDTO){
        Agenda agenda = new Agenda();
        Cooperativa cooperativa = new Cooperativa();
        Condominio condominio = new Condominio();

        agenda.setDatRetirada(agendaDTO.getDatRetirada());

        cooperativa.setId(agendaDTO.getFkCooperativa());
        condominio.setId(agendaDTO.getFkCondominio());

        agenda.setCondominio(condominio);
        agenda.setCooperativa(cooperativa);
        return agenda;
    }
}
