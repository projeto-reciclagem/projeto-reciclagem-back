package com.projeto.sprint.projetosprint.controller.agenda;


import com.projeto.sprint.projetosprint.controller.agenda.dto.AgendaCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.agenda.dto.AgendaResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Agenda;
import com.projeto.sprint.projetosprint.service.agenda.AgendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendaController {

    private final AgendaService service;

    @GetMapping("/listar")
    public ResponseEntity<List<AgendaResponseDTO>> listarAgendamentos() {
        return ResponseEntity.ok(
                this.service.listarAgendamentos());
    }

    // Criar um novo agendamento
    @PostMapping("/cadastrar")
    public ResponseEntity<AgendaResponseDTO> cadastrarAgendaColeta(@Valid @RequestBody AgendaCriacaoDTO dados) {
        return ResponseEntity.status(201).body(
                AgendaMapper.of(
                    this.service.cadastrarAgenda(dados)
                ));
    }

    // Atualizar um agendamento existente
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Agenda> atualizarAgendamento(@PathVariable int id,
                                                       @Valid @RequestBody Agenda dados) {
        dados.setId(id);
        return ResponseEntity.ok(this.service.atualizarAgendamento(dados));
    }

    // Excluir um agendamento
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> excluirAgendamento(@PathVariable int id) {
        this.service.excluirAgendamento(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/data")
    public ResponseEntity<List<AgendaResponseDTO>> buscarPorData(@RequestParam LocalDate data, @RequestParam int idCondominio, @RequestParam int idCooperativa){
        LocalDateTime dataConsulta = data.atStartOfDay();

        List<Agenda> agendamentos = this.service.buscarPorData(dataConsulta, idCondominio, idCooperativa);
        return ResponseEntity.ok(
                agendamentos.stream().map(AgendaMapper :: of).toList()
        );
    }

    @GetMapping("/coletas/ultima-semana/{id}")
    public ResponseEntity<Integer> coletasUltimasSemanas(@PathVariable int id){
        return ResponseEntity.ok(
          this.service.coletasUltimasSemanas(id)
        );
    }

    @GetMapping("/atendimentos/ultima-semana/{id}")
    public ResponseEntity<Integer> condominiosAtendidosUltimaSemana(@PathVariable int id){
        return ResponseEntity.ok(
                this.service.condominiosAtendidosUltimaSemana(id)
        );
    }

    @GetMapping("/historico")
    public ResponseEntity<List<AgendaResponseDTO>> historicoAgendamento(@RequestParam int idCondominio, @RequestParam int idCooperativa){
        return ResponseEntity.ok(
                this.service.historicoDePedidos(idCondominio, idCooperativa)
                        .stream().map(AgendaMapper :: of).toList()
        );
    }

    @GetMapping("/coletas-solicitadas/mes/{id}")
    public ResponseEntity<Integer> coletasSolicitadasUltimoMes(@PathVariable int id){
        return ResponseEntity.ok(
            this.service.coletasSolicitadasUltimoMes(id)
        );
    }

    @GetMapping("/ultima-coleta/{id}")
    public ResponseEntity<Integer> ultimaColeta(@PathVariable int id){
        return ResponseEntity.ok(
                this.service.ultimaColetaFeita(id)
        );
    }
}
