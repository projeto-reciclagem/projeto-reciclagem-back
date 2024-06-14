package com.projeto.sprint.projetosprint.controller.agendamento;

import com.projeto.sprint.projetosprint.controller.agendamento.dto.AgendaCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.agendamento.dto.AgendaResponse;
import com.projeto.sprint.projetosprint.controller.agendamento.dto.AgendaResponseDTO;
import com.projeto.sprint.projetosprint.controller.condominio.CondominioMapper;
import com.projeto.sprint.projetosprint.controller.cooperativa.CooperativaMapper;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Agenda;
import com.projeto.sprint.projetosprint.domain.entity.condominio.Condominio;
import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;

import java.time.format.DateTimeFormatter;

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

    public static Agenda of(AgendaCriacaoDTO agendaDTO) {
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

    public static AgendaResponse ofResponse(Agenda agenda) {

        if (agenda != null) {

            AgendaResponse agendaResponse = new AgendaResponse();

            agendaResponse.setId(agenda.getId());
            agendaResponse.setBags(agenda.getQtBag());
            agendaResponse.setEndereco(agenda.getCondominio().getUsuario().getEndereco().getLogradouro());
            agendaResponse.setData(agenda.getDatAgendamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            agendaResponse.setValor(0.0);

            agendaResponse.setHoraColeta(
                    agenda.getDatRetirada().toLocalTime().format(
                            java.time.format.DateTimeFormatter.ofPattern("HH:mm")
                    )
            );

            agendaResponse.setStatus(agenda.getStatus().toString());
            agendaResponse.setCondominio(agenda.getCondominio().getNome());
            agendaResponse.setResponsavel(agenda.getCooperativa().getNome());

            return agendaResponse;
        }
        return null;
    }
}
