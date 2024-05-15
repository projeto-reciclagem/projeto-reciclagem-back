package com.projeto.sprint.projetosprint.controller.agendamento;

import com.projeto.sprint.projetosprint.controller.agendamento.dto.AgendaCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.agendamento.dto.AgendaListagemDTO;
import com.projeto.sprint.projetosprint.controller.agendamento.dto.AgendaListagemResponseDTO;
import com.projeto.sprint.projetosprint.controller.agendamento.dto.AgendaResponseDTO;
import com.projeto.sprint.projetosprint.controller.condominio.CondominioMapper;
import com.projeto.sprint.projetosprint.controller.cooperativa.CooperativaMapper;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Agenda;
import com.projeto.sprint.projetosprint.domain.entity.condominio.Condominio;
import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;

import java.util.List;
import java.util.stream.Collectors;

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

    public static AgendaListagemDTO toDTO(Agenda agenda) {
        AgendaListagemDTO dto = new AgendaListagemDTO();
        dto.setId(agenda.getId());
        dto.setDatAgendamento(agenda.getDatAgendamento());
        dto.setDatRetirada(agenda.getDatRetirada());
        dto.setQtdBags(agenda.getQtBag());
        dto.setStatus(agenda.getStatus());
        dto.setCooperativa(CooperativaMapper.of(agenda.getCooperativa()));
        dto.setCondominio(CondominioMapper.of(agenda.getCondominio()));

        return dto;
    }

    public static List<AgendaListagemDTO> toDTOList(List<Agenda> schedules) {
        return schedules.stream().map(AgendaMapper::toDTO).collect(Collectors.toList());
    }

    public static Agenda of(AgendaCriacaoDTO agendaDTO){
        Agenda agenda = new Agenda();
        Cooperativa cooperativa = new Cooperativa();
        Condominio condominio = new Condominio();

        agenda.setDatRetirada(agendaDTO.getDatRetirada());
        agenda.setQtBag(agendaDTO.getQtBag());
        agenda.setStatus(agendaDTO.getStatus());
        cooperativa.setId(agendaDTO.getFkCooperativa());
        condominio.setId(agendaDTO.getFkCondominio());

        agenda.setCondominio(condominio);
        agenda.setCooperativa(cooperativa);
        return agenda;
    }
}
