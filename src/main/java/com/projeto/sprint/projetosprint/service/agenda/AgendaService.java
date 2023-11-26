package com.projeto.sprint.projetosprint.service.agenda;

import com.projeto.sprint.projetosprint.controller.agenda.AgendaMapper;
import com.projeto.sprint.projetosprint.controller.agenda.dto.AgendaCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.agenda.dto.AgendaResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Agenda;
import com.projeto.sprint.projetosprint.domain.repository.AgendaRepository;
import com.projeto.sprint.projetosprint.exception.EntidadeNaoEncontradaException;
import com.projeto.sprint.projetosprint.service.condominio.CondominioService;
import com.projeto.sprint.projetosprint.service.cooperativa.CooperativaService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AgendaService {

    private final AgendaRepository repository;
    private final CooperativaService cooperativaService;
    private final CondominioService condominioService;


    public AgendaService(AgendaRepository repository, CooperativaService cooperativaService, CondominioService condominioService) {
        this.repository = repository;
        this.cooperativaService = cooperativaService;
        this.condominioService = condominioService;
    }

    public List<AgendaResponseDTO> listarAgendamentos(){
        List<Agenda> agendamentos = this.repository.findAll();
        return agendamentos.stream().map(AgendaMapper :: of).toList();
    }

    public Agenda cadastrarAgenda(AgendaCriacaoDTO dados){
        Agenda agenda = AgendaMapper.of(dados);
        agenda.setDatAgendamento(LocalDateTime.now());

        agenda.setCooperativa(
                this.cooperativaService.buscaCoperativaId(dados.getFkCooperativa())
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

    public List<Agenda> buscarPorData(LocalDateTime data){
        LocalDateTime endData = data.toLocalDate().atTime(23,59,49);
        return this.repository.findByDatRetirada(data, endData);
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

}
