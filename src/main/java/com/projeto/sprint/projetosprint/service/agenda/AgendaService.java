package com.projeto.sprint.projetosprint.service.agenda;

import com.projeto.sprint.projetosprint.controller.agenda.AgendaMapper;
import com.projeto.sprint.projetosprint.controller.agenda.dto.AgendaCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.agenda.dto.AgendaResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Agenda;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Status;
import com.projeto.sprint.projetosprint.domain.repository.AgendaRepository;
import com.projeto.sprint.projetosprint.exception.EntidadeNaoEncontradaException;
import com.projeto.sprint.projetosprint.service.condominio.CondominioService;
import com.projeto.sprint.projetosprint.service.cooperativa.CooperativaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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

    public List<Agenda> historicoDePedidos(int idCondominio, int idCooperativa){
        if(idCondominio != 0){
            return this.repository.findByCondominioId(idCondominio);
        }
        else if (idCooperativa != 0){
            return this.repository.findByCooperativaId(idCooperativa);
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

    public Integer ultimaColetaFeita(int idCondominio) {
        Optional<Agenda> agenda = this.repository.ultimaColetaFeita(idCondominio, Status.CONCLUIDO);

        if (agenda.isEmpty()) return 0;

        return (int) ChronoUnit.DAYS.between(agenda.get().getDatRetirada().toLocalDate(), LocalDate.now());
    }

}
