package com.projeto.sprint.projetosprint.service.agenda;

import com.projeto.sprint.projetosprint.controller.agendamento.AgendaMapper;
import com.projeto.sprint.projetosprint.controller.agendamento.dto.*;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Agenda;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Status;
import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;
import com.projeto.sprint.projetosprint.domain.repository.AgendaRepository;
import com.projeto.sprint.projetosprint.exception.DataInvalidaException;
import com.projeto.sprint.projetosprint.exception.EntidadeNaoEncontradaException;
import com.projeto.sprint.projetosprint.service.condominio.CondominioService;
import com.projeto.sprint.projetosprint.service.cooperativa.CooperativaService;
import com.projeto.sprint.projetosprint.util.CalcularPorcentagem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgendaService {

    private final AgendaRepository repository;
    private final CooperativaService cooperativaService;
    private final CondominioService condominioService;

    public AgendaListagemResponseDTO listarAgendamentos(String email, String nomeCliente, Status statusAgendamento, int pageIndex, int perPage) {
        Cooperativa cooperativa = cooperativaService.buscarCooperativa(email);
        int idCooperativa = cooperativa.getId();

        PageRequest pageRequest = PageRequest.of(pageIndex, perPage, Sort.by(
                Sort.Order.desc("datAgendamento")
        ));

        List<Agenda> schedules = repository.findAgendamentosPorCooperativa(idCooperativa, statusAgendamento, nomeCliente, pageRequest);
        long totalCount = repository.contagemDeAgendamentosPorCooperativa(idCooperativa, statusAgendamento, nomeCliente);

        List<AgendaListagemDTO> schedulesListDTO = AgendaMapper.toDTOList(schedules);

        AgendaListagemResponseDTO response = new AgendaListagemResponseDTO();
        response.setSchedules(schedulesListDTO);

        AgendaListagemResponseDTO.Meta meta = new AgendaListagemResponseDTO.Meta();

        meta.setPageIndex(pageIndex);
        meta.setPerPage(perPage);
        meta.setTotalCount(totalCount);

        response.setMeta(meta);

        return response;
    }

    public Agenda cadastrarAgenda(AgendaCriacaoDTO dados){
        Agenda agenda = AgendaMapper.of(dados);
        agenda.setDatAgendamento(LocalDateTime.now());

        agenda.setCooperativa(
                this.cooperativaService.buscarCoperativaId(dados.getFkCooperativa())
        );

        agenda.setCondominio(
                this.condominioService.buscaCondominioId(dados.getFkCondominio())
        );

        return this.repository.save(agenda);
    }

    public Agenda atualizarAgendamento(Agenda dados){
        if(repository.existsById(dados.getId())){
            return this.repository.save(dados);
        }
        throw new EntidadeNaoEncontradaException("Campo id inválido");
    }

    public void excluirAgendamento(int id){
        if (this.repository.existsById(id)){
            this.repository.deleteById(id);
        }
        throw new EntidadeNaoEncontradaException("Campo id inválido");
    }

    public AgendamentosRealizadosDTO getAgendamentosRealizados(String email){
        LocalDate mesAtual = LocalDate.now();

        Integer realizadoMesAtual = this.repository.findAgendamentosRealizados(
                email,
                mesAtual.with(TemporalAdjusters.firstDayOfMonth()).atTime(0,0,0),
                mesAtual.with(TemporalAdjusters.lastDayOfMonth()).atTime(23,59,59)
        );

        LocalDate mesAnterior = mesAtual.minusMonths(1);
        Integer realizadoMesAnterior = this.repository.findAgendamentosRealizados(
                email,
                mesAnterior.with(TemporalAdjusters.firstDayOfMonth()).atTime(0,0,0),
                mesAnterior.with(TemporalAdjusters.lastDayOfMonth()).atTime(23,59,59)
        );

        Double p = 0.0;

        if (realizadoMesAnterior != 0) {
            p = CalcularPorcentagem.porcentagemAumentou(realizadoMesAtual, realizadoMesAnterior);
        }

        return new AgendamentosRealizadosDTO(realizadoMesAtual, realizadoMesAnterior, p);
    }

    public CanceladosUltimoMesDTO totalCanceladoMes(String email) {
        LocalDate mesAtual = LocalDate.now();

        Integer canceladoMesAtual = this.repository.findAgendamentosCancelados(
            email,
            mesAtual.with(TemporalAdjusters.firstDayOfMonth()).atTime(0,0,0),
            mesAtual.with(TemporalAdjusters.lastDayOfMonth()).atTime(23,59,59)
        );

        LocalDate mesAnterior = mesAtual.minusMonths(1);
        Integer canceladoMesAnterior = this.repository.findAgendamentosCancelados(
                email,
            mesAnterior.with(TemporalAdjusters.firstDayOfMonth()).atTime(0,0,0),
            mesAnterior.with(TemporalAdjusters.lastDayOfMonth()).atTime(23,59,59)
        );

        Double p = 0.0;

        if (canceladoMesAnterior != 0) {
            p = CalcularPorcentagem.porcentagemAumentou(canceladoMesAtual, canceladoMesAnterior);
        }

        return new CanceladosUltimoMesDTO(canceladoMesAtual, canceladoMesAnterior, p.intValue());
    }

    public void approveSchedule(int scheduleId) {
        if (this.repository.existsById(scheduleId)) {
            this.repository.approveScheduleId(scheduleId);
        }
    }

    public void completeSchedule(int scheduleId) {
        if (this.repository.existsById(scheduleId)) {
            this.repository.completeScheduleId(scheduleId);
        }
    }

    public void cancelSchedule(int scheduleId) {
        if (this.repository.existsById(scheduleId)) {
            this.repository.cancelScheduleId(scheduleId);
        }
    }

    public List<AgendamentoDiarioDTO> getSchedulesRealizedInPeriod(String email, LocalDate initialDate, LocalDate finalDate) {
        List<AgendamentoDiarioDTO> schedulesRealizedInPeriod = new ArrayList<AgendamentoDiarioDTO>();
        Cooperativa cooperativa = cooperativaService.buscarCooperativa(email);

        if (initialDate.equals(finalDate.minusDays(7))) {
            for (int i = 0; i <= 7; i++) {
                LocalDateTime initialDateStartTime = initialDate.atTime(0,0,0);
                LocalDateTime initialDateEndTime = initialDate.atTime(23,59,59);

                Integer schedulesCollected = this.repository.countRealizedSchedulesInPeriod(cooperativa.getId(), initialDateStartTime, initialDateEndTime);

                schedulesRealizedInPeriod.add(new AgendamentoDiarioDTO(initialDate, schedulesCollected));

                initialDate = initialDate.plusDays(1);
            }
            
            return schedulesRealizedInPeriod;
        } else {
            throw new DataInvalidaException("Intervalo de datas inválido.");
        }
    }

    public Agenda getScheduleDetails(int scheduleId) {
        return this.repository.findById(scheduleId).orElseThrow(
                () -> new EntidadeNaoEncontradaException("Agendamento não encontrado!"));
    }
}
