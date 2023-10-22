package com.projeto.sprint.projetosprint.controller;


import com.projeto.sprint.projetosprint.domain.repository.AgendaRepository;
import com.projeto.sprint.projetosprint.exception.AgendamentoNotFoundException;
import com.projeto.sprint.projetosprint.domain.agenda.Agenda;
import com.projeto.sprint.projetosprint.service.agenda.AgendaService;
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

    @GetMapping("/listar-agendamentos")
    public ResponseEntity<List<Agenda>> listarAgendamentos() {
        return ResponseEntity.ok(
                this.service.listarAgendamentos());
    }

    // Criar um novo agendamento
    @PostMapping("/cadastrar-agendamento")
    public ResponseEntity<Agenda> cadastrarAgendaColeta(@RequestBody Agenda dados) {
            dados.setDataAgendamento(LocalDateTime.now());
            // Salva o registro no banco de dados usando o repositório
            return ResponseEntity.status(201).body(
                    this.service.cadastrarAgenda(dados));
    }

    // Atualizar um agendamento existente
    @PutMapping("/atualizar-agendamento/{id}")
    public ResponseEntity<Agenda> atualizarAgendamento(@PathVariable int id, @RequestBody Agenda dados) {
        dados.setId(id);
        return ResponseEntity.ok(this.service.atualizarAgendamento(dados));
    }

    // Excluir um agendamento
    @DeleteMapping("/deletar-agendamento/{id}")
    public ResponseEntity<Void> excluirAgendamento(@PathVariable int id) {
        this.service.excluirAgendamento(id);
        return ResponseEntity.noContent().build();
    }

}
