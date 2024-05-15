package com.projeto.sprint.projetosprint.service.agenda;

import com.projeto.sprint.projetosprint.controller.agendamento.AgendaMapper;
import com.projeto.sprint.projetosprint.controller.agendamento.dto.AgendaCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.agendamento.dto.AgendaRealizadasMesDTO;
import com.projeto.sprint.projetosprint.controller.agendamento.dto.AgendaResponseDTO;
import com.projeto.sprint.projetosprint.controller.agendamento.dto.CanceladosUltimoMesDTO;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Agenda;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Status;
import com.projeto.sprint.projetosprint.domain.repository.AgendaRepository;
import com.projeto.sprint.projetosprint.exception.EntidadeNaoEncontradaException;
import com.projeto.sprint.projetosprint.service.condominio.CondominioService;
import com.projeto.sprint.projetosprint.service.cooperativa.CooperativaService;
import com.projeto.sprint.projetosprint.util.CalcularPorcentagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgendaService {

    private final AgendaRepository repository;
    private final CooperativaService cooperativaService;
    private final CondominioService condominioService;


    public List<AgendaResponseDTO> listarAgendamentos(){
        List<Agenda> agendamentos = this.repository.findAll();
        return agendamentos.stream().map(AgendaMapper :: of).toList();
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

    public List<Agenda> buscarPorData(LocalDateTime data, int idCondominio, int idCooperativa){
        LocalDateTime endData = data.toLocalDate().atTime(23, 59, 59);

        if(idCondominio != 0){
            return this.repository.findByDatRetiradaCondominio(idCondominio, data, endData);
        }
        else if (idCooperativa != 0){
            return this.repository.findByDatRetiradaCooperativa(idCooperativa, data, endData);
        }
        return null;
    }

    public Integer coletasUltimasSemanas(int idCooperativa){
        LocalDateTime datAtual = LocalDateTime.now();
        LocalDateTime datSemanaPassada = datAtual.minusWeeks(1);

        return this.repository.contagemAgendamentoUltimaSemana(idCooperativa, datSemanaPassada, datAtual);
    }

    public Integer condominiosAtendidosUltimaSemana(int idCooperativa){
        LocalDateTime datAtual = LocalDateTime.now();
        LocalDateTime datSemanaPassada = datAtual.minusWeeks(1);

        return this.repository.condominiosAtendidosUltimaSemana(idCooperativa, datSemanaPassada, datAtual);
    }

    public List<Agenda> historicoDePedidos(int idCondominio, int idCooperativa, String nomeCliente, String statusAgendamento) {
        Status status = null;
        if (!statusAgendamento.isEmpty()) {
            status = Status.valueOf(statusAgendamento);
        }

        if (idCondominio != 0) {
            return this.repository.findByCondominio(idCondominio, nomeCliente, status);
        } else if (idCooperativa != 0) {
            return this.repository.findByCooperativa(idCooperativa, nomeCliente, status);
        }
        return null;
    }

    public Agenda buscarAgendaPorId(int id){
        return this.repository.findById(id).get();
    }

    public Integer coletasSolicitadasUltimoMes(int idCondominio){
        LocalDateTime dataAtual = LocalDateTime.now();

        return this.repository.countByCondominioId(idCondominio,
                dataAtual.minusMonths(1),
                dataAtual);
    }

    public Integer coletasRealizadasUltimoMes(int idCooperativa){
        LocalDateTime dataAtual = LocalDateTime.now();

        return this.repository.countByCooperativaId(idCooperativa,
                dataAtual.minusMonths(1),
                dataAtual);
    }

    public AgendaRealizadasMesDTO coletasRealizadasMes(int id){
        LocalDate mesAtual = LocalDate.now();

        Integer realizadoMesAtual = this.repository.realizadoMes(
                id,
                mesAtual.with(TemporalAdjusters.firstDayOfMonth()).atTime(0,0,0),
                mesAtual.with(TemporalAdjusters.lastDayOfMonth()).atTime(23,59,59)
        );

        LocalDate mesAnterior = mesAtual.minusMonths(1);
        Integer realizadoMesAnterior = this.repository.realizadoMes(
                id,
                mesAnterior.with(TemporalAdjusters.firstDayOfMonth()).atTime(0,0,0),
                mesAnterior.with(TemporalAdjusters.lastDayOfMonth()).atTime(23,59,59)
        );

        Double p = 0.0;

        if (realizadoMesAtual != 0) {
            p = CalcularPorcentagem.porcentagemAumentou(realizadoMesAtual, realizadoMesAnterior);
        }

        return new AgendaRealizadasMesDTO(realizadoMesAtual, realizadoMesAnterior, p.intValue());
    }

    public Integer ultimaColetaFeita(int idCondominio) {
        Optional<Agenda> agenda = this.repository.ultimaColetaFeita(idCondominio, Status.CONCLUIDO);

        if (agenda.isEmpty()) return 0;

        return (int) ChronoUnit.DAYS.between(agenda.get().getDatRetirada().toLocalDate(), LocalDate.now());
    }

    public CanceladosUltimoMesDTO totalCanceladoMes(int id){
        LocalDate mesAtual = LocalDate.now();

        Integer canceladoMesAtual = this.repository.canceladoMes(
            id,
            mesAtual.with(TemporalAdjusters.firstDayOfMonth()).atTime(0,0,0),
            mesAtual.with(TemporalAdjusters.lastDayOfMonth()).atTime(23,59,59)
        );

        LocalDate mesAnterior = mesAtual.minusMonths(1);
        Integer canceladoMesAnterior = this.repository.canceladoMes(
            id,
            mesAnterior.with(TemporalAdjusters.firstDayOfMonth()).atTime(0,0,0),
            mesAnterior.with(TemporalAdjusters.lastDayOfMonth()).atTime(23,59,59)
        );

        Double p = 0.0;

        if (canceladoMesAnterior != 0) {
            p = CalcularPorcentagem.porcentagemAumentou(canceladoMesAtual, canceladoMesAnterior);
        }

        return new CanceladosUltimoMesDTO(canceladoMesAtual, canceladoMesAnterior, p.intValue());
    }

    public void atualizarStatusAgendamento(int id, Status status){
        Agenda agenda = this.buscarAgendaPorId(id);
        agenda.setStatus(status);
        this.repository.save(agenda);
    }

}
