package com.projeto.sprint.projetosprint.controller.agendamento;


import com.projeto.sprint.projetosprint.controller.agendamento.dto.*;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Agenda;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Status;
import com.projeto.sprint.projetosprint.service.agenda.AgendaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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

    @Operation(summary = "Busca todos os agendamentos", description = "Busca todos os agendamentos do usuário")
    @GetMapping
    public ResponseEntity<AgendaListagemResponseDTO> listarAgendamentos(
            @RequestHeader(HttpHeaders.COOKIE) String auth,
            @RequestParam(required = false) String nomeCliente,
            @RequestParam(required = false) Status status,
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "8") int perPage
    ) {
        AgendaListagemResponseDTO schedulesListResponseDTO = service.listarAgendamentos(auth, nomeCliente, status, pageIndex, perPage);
        return ResponseEntity.ok(schedulesListResponseDTO);
    }

    // Criar um novo agendamento
    @Operation(summary = "Cria um novo agendamento", description = "Cria um novo agendamento de coleta.")
    @PostMapping
    public ResponseEntity<AgendaResponseDTO> cadastrarAgendaColeta(@Valid @RequestBody AgendaCriacaoDTO dados) {
        return ResponseEntity.status(201).body(
                AgendaMapper.of(
                    this.service.cadastrarAgenda(dados)
                ));
    }

    @Operation(summary = "Atualizar dados do agendamento", description = "Atualiza os dados de agendamento de coleta em específico")
    @PutMapping("/{id}")
    public ResponseEntity<Agenda> atualizarAgendamento(@PathVariable int id,
                                                       @Valid @RequestBody Agenda dados) {
        dados.setId(id);
        return ResponseEntity.ok(this.service.atualizarAgendamento(dados));
    }

    @Operation(summary = "Exclui um agendamento", description = "Exclui um agendamento em especifíco")
    @DeleteMapping("/{id}")
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
    public ResponseEntity<List<AgendaResponseDTO>> historicoAgendamento(@RequestParam int idCondominio,
                                                                        @RequestParam int idCooperativa,
                                                                        @RequestParam String nomeCliente,
                                                                        @RequestParam String statusAgendamento)
    {
        return ResponseEntity.ok(
                this.service.historicoDePedidos(idCondominio, idCooperativa, nomeCliente, statusAgendamento)
                        .stream().map(AgendaMapper :: of).toList()
        );
    }

    @GetMapping("/coletas-solicitadas/mes/{id}")
    public ResponseEntity<Integer> coletasSolicitadasUltimoMes(@PathVariable int id){
        return ResponseEntity.ok(
            this.service.coletasSolicitadasUltimoMes(id)
        );
    }

    @GetMapping("/coletas-realizadas/mes/{id}")
    public ResponseEntity<AgendaRealizadasMesDTO> totalRealizadoMes(@PathVariable int id){
        return ResponseEntity.ok(this.service.coletasRealizadasMes(id));
    }

    @GetMapping("/ultima-coleta/{id}")
    public ResponseEntity<Integer> ultimaColeta(@PathVariable int id){
        return ResponseEntity.ok(
                this.service.ultimaColetaFeita(id)
        );
    }

    @GetMapping("/total/cancelado/mes/{id}")
    public ResponseEntity<CanceladosUltimoMesDTO> totalCanceladoMes(@PathVariable int id){
        return ResponseEntity.ok(this.service.totalCanceladoMes(id));
    }

    @GetMapping("/historico/{id}")
    public ResponseEntity<AgendaResponseDTO> buscarAgendamentoPorId(@PathVariable int id){
        return ResponseEntity.ok(AgendaMapper.of(this.service.buscarAgendaPorId(id)));
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<Void> atualizarStatusAgendamento(@PathVariable int id, @RequestParam Status status) {
        this.service.atualizarStatusAgendamento(id, status);
        return ResponseEntity.status(200).build();
    }
}
