package com.projeto.sprint.projetosprint.controller.agenda;


import com.projeto.sprint.projetosprint.controller.agenda.dto.AgendaCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.agenda.dto.AgendaResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Agenda;
import com.projeto.sprint.projetosprint.service.agenda.AgendaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendaController {

    private final AgendaService service;

    public AgendaController(AgendaService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Agenda>> listarAgendamentos() {
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

}
