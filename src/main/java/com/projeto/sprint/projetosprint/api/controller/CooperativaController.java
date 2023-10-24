package com.projeto.sprint.projetosprint.api.controller;


import com.projeto.sprint.projetosprint.domain.cooperativa.Cooperativa;
import com.projeto.sprint.projetosprint.domain.repository.CooperativaRepository;
import com.projeto.sprint.projetosprint.service.usuario.cooperativa.CooperativaService;
import com.projeto.sprint.projetosprint.service.usuario.cooperativa.CooperativaLoginDto;
import com.projeto.sprint.projetosprint.service.usuario.cooperativa.CooperativaTokenDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/cooperativas")
@AllArgsConstructor
public class CooperativaController {
 @Autowired
    private CooperativaRepository repository;

    private CooperativaService cooperativaService;


    //LISTA TODAS AS COOPERATIVAS
    @GetMapping("/listar-cooperativa")
    public ResponseEntity<List<Cooperativa>> listarCooperativa(){

        List<Cooperativa> registros = this.repository.findAll();

        if (registros.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200)
                .body(registros);
    }

    //BUSCA A COOPERATIVA POR ID
    @GetMapping("/buscar-cooperativa-porid/{id}")
    public ResponseEntity<Cooperativa> buscarCooperativaPorId(@PathVariable int id){

        Optional<Cooperativa> registroOpt = this.repository.findById(id);

        if(registroOpt.isEmpty()){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200)
                .body(registroOpt.get());
    }

    //CADASTRA A COOPERATIVA
    @PostMapping
    public ResponseEntity<Cooperativa> cadastrarCooperativa(@RequestBody Cooperativa dados){

        //VALIDAÇÕES DOS CAMPOS
        if(dados.getNome() == null || dados.getNome().isBlank()){
            return ResponseEntity.status(400).build();
        }

        if(dados.getCnpj().isBlank() || dados.getCnpj().isEmpty()){
            return ResponseEntity.status(400).build();
        }

        if(dados.getEmail().isEmpty() || dados.getEmail().isBlank()){
            return ResponseEntity.status(400).build();
        }

        if(dados.getSenha().isEmpty() || dados.getSenha().isBlank()){
            return ResponseEntity.status(400).build();
        }

        //ADICIONANDO UMA NOVA COOPERATIVA
        repository.save(dados);
        return ResponseEntity.status(201).body(dados);
    }
    @PostMapping("/login")
    public ResponseEntity<CooperativaTokenDto> login(@RequestBody CooperativaLoginDto cooperativaLoginDto){
        CooperativaTokenDto cooperativaTokenDto = cooperativaService.autenticar(cooperativaLoginDto);
        return ResponseEntity.status(200).body(cooperativaTokenDto);
    }

    //ATUALIZANDO INFORMAÇÕES DA COOPERATIVA
    @PutMapping("/atualizar-cooperativa-corid/{id}")
    public ResponseEntity<Cooperativa> atualizarCooperativa(@PathVariable int id, @RequestBody Cooperativa dados){

        //VALIDAÇÕES DOS CAMPOS QUE SERÃO RECEBIDOS
        if(dados.getNome() == null || dados.getNome().isBlank()){
            return ResponseEntity.status(400).build();
        }

        if(dados.getCnpj().isBlank() || dados.getCnpj().isEmpty()){
            return ResponseEntity.status(400).build();
        }

        if(dados.getEmail().isEmpty() || dados.getEmail().isBlank()){
            return ResponseEntity.status(400).build();
        }

        if(dados.getSenha().isEmpty() || dados.getSenha().isBlank()){
            return ResponseEntity.status(400).build();
        }

        if(!this.repository.existsById(id)){
            return ResponseEntity.status(404).build();
        }

        //ATUALIZANDO A COOPERATIVA
        dados.setId(id);
        this.repository.save(dados);
        return ResponseEntity.status(200).body(dados);
    }

    //DELETANDO UMA COOPERATIVA
    @DeleteMapping("/deletarCooperativaPorId/{id}")
    public ResponseEntity<Void> deletarCooperativaPorId(@PathVariable int id){

        if(!this.repository.existsById(id)){
            return ResponseEntity.status(404).build();
        }

        this.repository.deleteById(id);
        return ResponseEntity.status(204).build();
    }

}
