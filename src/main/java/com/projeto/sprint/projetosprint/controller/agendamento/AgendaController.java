package com.projeto.sprint.projetosprint.controller.agendamento;

import com.projeto.sprint.projetosprint.controller.agendamento.dto.*;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Agenda;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Status;
import com.projeto.sprint.projetosprint.service.agenda.AgendaService;
import com.projeto.sprint.projetosprint.util.annotations.currentUser.CurrentUser;
import com.projeto.sprint.projetosprint.util.annotations.currentUser.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendaController {

    private final AgendaService service;

    @Operation(summary = "Busca todos os agendamentos", description = "Busca todos os agendamentos do usuário")
    @CurrentUser
    @GetMapping
    public ResponseEntity<AgendaListagemResponseDTO> listarAgendamentos(
            @RequestParam(required = false) String nomeCliente,
            @RequestParam(required = false) Status status,
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "8") int perPage
    ) {
        String userEmail = UserContextHolder.getUser();
        AgendaListagemResponseDTO schedulesListResponseDTO = service.listarAgendamentos(userEmail, nomeCliente, status, pageIndex, perPage);
        return ResponseEntity.ok(schedulesListResponseDTO);
    }

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

    @Operation(summary = "Aprovar um agendamento", description = "Aprova um agendamento")
    @PatchMapping("/{scheduleId}/approve")
    public ResponseEntity<Void> approveSchedule(@PathVariable int scheduleId) {
        this.service.approveSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Concluir um agendamento", description = "Concluir um agendamento")
    @PatchMapping("/{scheduleId}/complete")
    public ResponseEntity<Void> completeSchedule(@PathVariable int scheduleId) {
        this.service.completeSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cancelar um agendamento", description = "Cancelar um agendamento")
    @PatchMapping("/{scheduleId}/cancel")
    public ResponseEntity<Void> cancelSchedule(@PathVariable int scheduleId) {
        this.service.cancelSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Agendamentos realizados", description = "Retorna a quantidade total de agendamentos realizados no mês")
    @CurrentUser
    @GetMapping("/realizados")
    public ResponseEntity<AgendamentosRealizadosDTO> completedSchedules() {
        String email = UserContextHolder.getUser();
        AgendamentosRealizadosDTO response = this.service.getAgendamentosRealizados(email);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Agendamentos cancelados", description = "Retorna a quantidade total de agendamentos cancelados no mês")
    @CurrentUser
    @GetMapping("/cancelados")
    public ResponseEntity<CanceladosUltimoMesDTO> cancelledSchedules(){
        String email = UserContextHolder.getUser();
        return ResponseEntity.ok(this.service.totalCanceladoMes(email));
    }

    @Operation(summary = "Agendamentos Realizados no período", 
        description = "Retorna a quantidadde de agendamentos realizados no período selecionado")
    @CurrentUser
    @GetMapping("/realizados-periodo")
    public ResponseEntity<List<AgendamentoDiarioDTO>> getSchedulesRealizedInPeriod(@RequestParam LocalDate initialDate, @RequestParam LocalDate finalDate) {
        String email = UserContextHolder.getUser();
        return ResponseEntity.ok(this.service.getSchedulesRealizedInPeriod(email, initialDate, finalDate));
    }
}
