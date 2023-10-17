package com.projeto.sprint.projetosprint.controller;

import com.projeto.sprint.projetosprint.entity.AgendaColeta;
import com.projeto.sprint.projetosprint.entity.Condominio;
import com.projeto.sprint.projetosprint.excepition.AgendamentoNotFoundException;
import com.projeto.sprint.projetosprint.repository.AgendaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendaController {
    private final AgendaRepository agendaRepository;

    public AgendaController(AgendaRepository repository) {
        this.agendaRepository = repository;
    }

    @GetMapping
    public List<AgendaColeta> listarAgendamentos() {
        return agendaRepository.findAll();
    }

    // Criar um novo agendamento
    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarAgendaColeta(@RequestBody AgendaColeta agendamentos) {
        try {
            AgendaColeta agendaColeta = new AgendaColeta();
            agendaColeta.setCooperativa(agendamentos.getCooperativa());
            agendaColeta.setCondominio(agendamentos.getCondominio());
            agendaColeta.setDataAgendamento(LocalDateTime.now());

            // Salva o registro no banco de dados usando o repositório
            agendaRepository.save(agendaColeta);

            return ResponseEntity.status(201).body("Agenda de coleta cadastrada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao cadastrar a agenda de coleta: " + e.getMessage());
        }
    }

    // Atualizar um agendamento existente
    @PutMapping("/{id}")
    public AgendaColeta atualizarAgendamento(@PathVariable int id, @RequestBody AgendaColeta agendamento) {

        if (agendaRepository.existsById(id)) {
            agendamento.setId(id); // Defina o ID para garantir que você está atualizando o registro correto
            return agendaRepository.save(agendamento);
        } else {
            throw new AgendamentoNotFoundException("Agendamento não encontrado com o ID: " + id);
        }
    }

    // Excluir um agendamento
    @DeleteMapping("/{id}")
    public void excluirAgendamento(@PathVariable int id) {

        if (agendaRepository.existsById(id)) {
            agendaRepository.deleteById(id);
        } else {
            throw new AgendamentoNotFoundException("Agendamento não encontrado com o ID: " + id);
        }
    }

}
